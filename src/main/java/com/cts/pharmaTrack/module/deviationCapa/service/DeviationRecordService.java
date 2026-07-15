package com.cts.pharmaTrack.module.deviationCapa.service;

import com.cts.pharmaTrack.common.exception.DuplicateResourceException;
import com.cts.pharmaTrack.common.exception.InvalidStatusTransitionException;
import com.cts.pharmaTrack.common.exception.ResourceNotFoundException;
import com.cts.pharmaTrack.module.deviationCapa.dto.DeviationRecordRequest;
import com.cts.pharmaTrack.module.deviationCapa.dto.DeviationRecordResponse;
import com.cts.pharmaTrack.module.deviationCapa.entity.DeviationRecord;
import com.cts.pharmaTrack.module.deviationCapa.repository.CAPARecordRepository;
import com.cts.pharmaTrack.module.deviationCapa.repository.DeviationRecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Business logic for quality deviations: logging, lookup, update and safe deletion.
 * The primary key {@code deviationId} is supplied by the client (e.g. {@code DEV001}).
 */
@Service
@RequiredArgsConstructor
@Transactional
public class DeviationRecordService {

    private static final Logger logger = LoggerFactory.getLogger(DeviationRecordService.class);
    private static final String DEFAULT_STATUS = "OPN";

    private final DeviationRecordRepository deviationRepository;
    private final CAPARecordRepository capaRepository;

    public DeviationRecordResponse create(DeviationRecordRequest request) {
        logger.info("Executing create with deviationId: {}", request.getDeviationId());
        if (deviationRepository.existsById(request.getDeviationId())) {
            throw new DuplicateResourceException(
                    "deviationId already exists: " + request.getDeviationId());
        }
        DeviationRecord deviation = new DeviationRecord();
        deviation.setDeviationId(request.getDeviationId());
        apply(deviation, request);
        deviation.setStatus(StringUtils.hasText(request.getStatus())
                ? toShortStatus(request.getStatus()) : DEFAULT_STATUS);
        return toResponse(deviationRepository.save(deviation));
    }

    @Transactional(readOnly = true)
    public List<DeviationRecordResponse> getAll() {
        logger.info("Executing getAll");
        List<DeviationRecord> deviations = deviationRepository.findAll();
        if (deviations.isEmpty()) {
            throw new ResourceNotFoundException("No deviation records found");
        }
        return deviations.stream().map(this::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public DeviationRecordResponse getById(String deviationId) {
        logger.info("Executing getById with deviationId: {}", deviationId);
        return toResponse(findOrThrow(deviationId));
    }

    public DeviationRecordResponse update(DeviationRecordRequest request) {
        logger.info("Executing update with deviationId: {}", request.getDeviationId());
        DeviationRecord deviation = findOrThrow(request.getDeviationId());
        apply(deviation, request);
        if (StringUtils.hasText(request.getStatus())) {
            deviation.setStatus(toShortStatus(request.getStatus()));
        }
        return toResponse(deviationRepository.save(deviation));
    }

    @Transactional(readOnly = true)
    public List<DeviationRecordResponse> getByEntity(String relatedEntityType, String relatedEntityId) {
        logger.info("Executing getByEntity with entityType: {}, entityId: {}", relatedEntityType, relatedEntityId);
        List<DeviationRecord> deviations = deviationRepository.findByRelatedEntityTypeAndRelatedEntityId(
                relatedEntityType, relatedEntityId);
        if (deviations.isEmpty()) {
            throw new ResourceNotFoundException("No deviation records found for entity: " + relatedEntityId);
        }
        return deviations.stream().map(this::toResponse).toList();
    }

    public void updateStatus(String deviationId, String newStatus) {
        logger.info("Executing updateStatus with deviationId: {}, newStatus: {}", deviationId, newStatus);
        DeviationRecord deviation = findOrThrow(deviationId);
        deviation.setStatus(toShortStatus(newStatus));
        deviationRepository.save(deviation);
    }

    public void delete(String deviationId) {
        logger.info("Executing delete with deviationId: {}", deviationId);
        DeviationRecord deviation = findOrThrow(deviationId);
        if (capaRepository.existsByDeviationId(deviationId)) {
            throw new InvalidStatusTransitionException(
                    "Cannot delete deviation with linked CAPA records: " + deviationId);
        }
        deviationRepository.delete(deviation);
    }

    private void apply(DeviationRecord deviation, DeviationRecordRequest request) {
        deviation.setRelatedEntityType(request.getRelatedEntityType());
        deviation.setRelatedEntityId(request.getRelatedEntityId());
        deviation.setDescription(request.getDescription());
        deviation.setDetectedById(request.getDetectedById());
        deviation.setDetectionDate(request.getDetectionDate());
        deviation.setImpact(request.getImpact());
    }

    private DeviationRecord findOrThrow(String deviationId) {
        return deviationRepository.findById(deviationId)
                .orElseThrow(() -> new ResourceNotFoundException("DeviationRecord", deviationId));
    }

    private DeviationRecordResponse toResponse(DeviationRecord d) {
        return new DeviationRecordResponse(
                d.getDeviationId(),
                d.getRelatedEntityType(),
                d.getRelatedEntityId(),
                d.getDescription(),
                d.getDetectedById(),
                d.getDetectionDate(),
                d.getImpact(),
                d.getStatus());
    }

    private String toShortStatus(String status) {
        if (!StringUtils.hasText(status)) {
            return DEFAULT_STATUS;
        }
        String normalized = status.trim().toUpperCase();
        return switch (normalized) {
            case "OPEN", "OPN" -> "OPN";
            case "CLOSED", "CLS" -> "CLS";
            case "DELETED", "DEL" -> "DEL";
            case "INPROGRESS", "IN PROGRESS", "INP" -> "INP";
            case "CANCELLED", "CANCELED" -> "CNL";
            default -> normalized.length() >= 3 ? normalized.substring(0, 3) : normalized;
        };
    }
    }
