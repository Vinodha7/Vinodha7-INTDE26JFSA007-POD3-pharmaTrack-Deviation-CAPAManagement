package com.cts.pharmaTrack.module.deviationCapa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

/**
 * A corrective and preventive action plan raised against a {@link DeviationRecord}.
 *
 * <p>Maps to the {@code capa_record} table. The primary key {@code capaId} is a
 * client-supplied string (e.g. {@code CAPA001}). {@code deviationId} links back
 * to the originating deviation.
 */
@Entity
@Table(name = "capa_record")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CAPARecord {

    @Id
    @Column(name = "capaId", length = 20)
    private String capaId;

    @Column(name = "deviationId", nullable = false, length = 20)
    private String deviationId;

    @Column(name = "rootCause", length = 2000)
    private String rootCause;

    @Column(name = "correctiveAction", length = 2000)
    private String correctiveAction;

    @Column(name = "preventiveAction", length = 2000)
    private String preventiveAction;

    @Column(name = "assignedToId", length = 20)
    private String assignedToId;

    @Column(name = "dueDate")
    private LocalDate dueDate;

    @Column(name = "closedDate")
    private LocalDate closedDate;

    /** Open / InProgress / Closed / Overdue */
    @Column(name = "status", length = 20)
    private String status;
}
