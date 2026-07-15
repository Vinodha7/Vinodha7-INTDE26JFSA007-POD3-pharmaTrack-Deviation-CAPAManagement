package com.cts.pharmaTrack.module.deviationCapa.controller;

import com.cts.pharmaTrack.common.response.ApiResponse;
import com.cts.pharmaTrack.module.deviationCapa.dto.DeviationRecordRequest;
import com.cts.pharmaTrack.module.deviationCapa.dto.DeviationRecordResponse;
import com.cts.pharmaTrack.module.deviationCapa.service.DeviationRecordService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@RestController
@RequestMapping("/pharmaTrack/deviationCapa")
@RequiredArgsConstructor
public class DeviationRecordController {

    private static final Logger logger = LoggerFactory.getLogger(DeviationRecordController.class);
    private final DeviationRecordService deviationService;

    @PostMapping("/createDeviation")
    public ResponseEntity<?> create(
            @Valid @RequestBody DeviationRecordRequest request) {
        logger.info("POST /createDeviation request received with deviationId: {}", request.getDeviationId());
        deviationService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(java.util.Map.of("message", "Deviation logged successfully"));
    }

    @GetMapping("/retrieveDeviations")
    public ResponseEntity<List<DeviationRecordResponse>> retrieveAll() {
        logger.info("GET /retrieveDeviations request received");
        return ResponseEntity.ok(deviationService.getAll());
    }

    @GetMapping("/retrieveDeviationById/{deviationId}")
    public ResponseEntity<DeviationRecordResponse> retrieveById(
            @PathVariable String deviationId) {
        logger.info("GET /retrieveDeviationById/{} request received with deviationId: {}", deviationId, deviationId);
        return ResponseEntity.ok(deviationService.getById(deviationId));
    }

    @GetMapping("/retrieveDeviationsByEntity/{relatedEntityType}/{relatedEntityId}")
    public ResponseEntity<List<DeviationRecordResponse>> retrieveByEntity(
            @PathVariable String relatedEntityType,
            @PathVariable String relatedEntityId) {
        logger.info("GET /retrieveDeviationsByEntity request received with entityType: {}, entityId: {}", relatedEntityType, relatedEntityId);
        return ResponseEntity.ok(deviationService.getByEntity(relatedEntityType, relatedEntityId));
    }

    @PutMapping("/updateDeviation/{deviationId}")
    public ResponseEntity<?> updateDeviation(
            @PathVariable String deviationId,
            @Valid @RequestBody DeviationRecordRequest request) {
        logger.info("PUT /updateDeviation/{} request received with deviationId: {}", deviationId, deviationId);
        request.setDeviationId(deviationId);
        deviationService.update(request);
        return ResponseEntity.ok(java.util.Map.of("message", "Deviation updated successfully"));
    }

    @PutMapping("/updateDeviationStatus/{deviationId}")
    public ResponseEntity<?> updateDeviationStatus(
            @PathVariable String deviationId,
            @RequestBody java.util.Map<String, String> body) {
        logger.info("PUT /updateDeviationStatus/{} request received with deviationId: {}", deviationId, deviationId);
        String newStatus = body.get("status");
        deviationService.updateStatus(deviationId, newStatus);
        return ResponseEntity.ok(java.util.Map.of("message", "Deviation status updated successfully"));
    }
}
