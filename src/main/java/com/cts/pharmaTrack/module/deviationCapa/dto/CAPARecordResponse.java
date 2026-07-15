package com.cts.pharmaTrack.module.deviationCapa.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

/**
 * Outbound representation of a CAPA record.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CAPARecordResponse {

    private String capaId;
    private String deviationId;
    private String rootCause;
    private String correctiveAction;
    private String preventiveAction;
    private String assignedToId;
    private LocalDate dueDate;
    private LocalDate closedDate;
    private String status;
}
