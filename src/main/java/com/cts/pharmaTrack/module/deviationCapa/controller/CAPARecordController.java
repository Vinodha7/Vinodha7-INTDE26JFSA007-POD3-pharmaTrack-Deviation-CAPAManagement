package com.cts.pharmaTrack.module.deviationCapa.controller;

import com.cts.pharmaTrack.common.response.ApiResponse;
import com.cts.pharmaTrack.module.deviationCapa.dto.CAPARecordRequest;
import com.cts.pharmaTrack.module.deviationCapa.dto.CAPARecordResponse;
import com.cts.pharmaTrack.module.deviationCapa.service.CAPARecordService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@RestController
@RequestMapping("/pharmaTrack/deviationCapa")
@RequiredArgsConstructor
public class CAPARecordController {

    private static final Logger logger = LoggerFactory.getLogger(CAPARecordController.class);
    private final CAPARecordService capaService;

    @PostMapping("/createCapa")
    public ResponseEntity<?> create(
            @Valid @RequestBody CAPARecordRequest request) {
        logger.info("POST /createCapa request received with capaId: {}", request.getCapaId());
        capaService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(java.util.Map.of("message", "CAPA created successfully"));
    }

    @GetMapping("/retrieveCapas")
    public ResponseEntity<List<CAPARecordResponse>> retrieveAll() {
        logger.info("GET /retrieveCapas request received");
        return ResponseEntity.ok(capaService.getAll());
    }

    @GetMapping("/retrieveCapaById/{capaId}")
    public ResponseEntity<CAPARecordResponse> retrieveById(@PathVariable String capaId) {
        logger.info("GET /retrieveCapaById/{} request received with capaId: {}", capaId, capaId);
        return ResponseEntity.ok(capaService.getById(capaId));
    }

    @GetMapping("/retrieveCapasByDeviation/{deviationId}")
    public ResponseEntity<List<CAPARecordResponse>> retrieveByDeviation(
            @PathVariable String deviationId) {
        logger.info("GET /retrieveCapasByDeviation/{} request received with deviationId: {}", deviationId, deviationId);
        return ResponseEntity.ok(capaService.getByDeviation(deviationId));
    }

    @PutMapping("/updateCapa/{capaId}")
    public ResponseEntity<?> updateCapa(
            @PathVariable String capaId,
            @Valid @RequestBody CAPARecordRequest request) {
        logger.info("PUT /updateCapa/{} request received with capaId: {}", capaId, capaId);
        request.setCapaId(capaId);
        capaService.update(request);
        return ResponseEntity.ok(java.util.Map.of("message", "CAPA updated successfully"));
    }

    @PutMapping("/updateCapaStatus/{capaId}")
    public ResponseEntity<?> updateCapaStatus(
            @PathVariable String capaId,
            @RequestBody java.util.Map<String, String> body) {
        logger.info("PUT /updateCapaStatus/{} request received with capaId: {}", capaId, capaId);
        String newStatus = body.get("status");
        capaService.updateStatus(capaId, newStatus);
        return ResponseEntity.ok(java.util.Map.of("message", "CAPA status updated successfully"));
    }
}
