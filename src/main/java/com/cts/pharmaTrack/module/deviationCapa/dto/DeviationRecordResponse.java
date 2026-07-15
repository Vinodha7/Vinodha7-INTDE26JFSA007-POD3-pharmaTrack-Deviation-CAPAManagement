package com.cts.pharmaTrack.module.deviationCapa.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

/**
 * Outbound representation of a deviation record.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DeviationRecordResponse {

    private String deviationId;
    private String relatedEntityType;
    private String relatedEntityId;
    private String description;
    private String detectedById;
    private LocalDate detectionDate;
    private String impact;
    private String status;
}
