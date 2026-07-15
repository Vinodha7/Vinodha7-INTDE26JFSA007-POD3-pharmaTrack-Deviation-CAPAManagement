package com.cts.pharmaTrack.module.deviationCapa.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

/**
 * Inbound payload for creating or updating a CAPA record.
 * {@code capaId} is the client-supplied primary key (e.g. {@code CAPA001}).
 */
@Getter
@Setter
public class CAPARecordRequest {

    @NotBlank(message = "capaId is required")
    private String capaId;

    @NotBlank(message = "deviationId is required")
    private String deviationId;

    private String rootCause;

    private String correctiveAction;

    private String preventiveAction;

    private String assignedToId;

    private LocalDate dueDate;

    private LocalDate closedDate;

    private String status;
}
