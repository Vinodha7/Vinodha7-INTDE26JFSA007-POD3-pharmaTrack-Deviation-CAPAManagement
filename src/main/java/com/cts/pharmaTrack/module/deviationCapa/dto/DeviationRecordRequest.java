package com.cts.pharmaTrack.module.deviationCapa.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

/**
 * Inbound payload for creating or updating a deviation record.
 * {@code deviationId} is the client-supplied primary key (e.g. {@code DEV001}).
 */
@Getter
@Setter
public class DeviationRecordRequest {

    @NotBlank(message = "deviationId is required")
    private String deviationId;

    @NotBlank(message = "relatedEntityType is required")
    private String relatedEntityType;

    @NotBlank(message = "relatedEntityId is required")
    private String relatedEntityId;

    private String description;

    private String detectedById;

    private LocalDate detectionDate;

    private String impact;

    private String status;
}
