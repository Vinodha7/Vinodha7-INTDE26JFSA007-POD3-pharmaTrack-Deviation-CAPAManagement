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
 * A logged quality deviation against a batch, trial or shipment.
 *
 * <p>Maps to the {@code deviation_record} table. The primary key
 * {@code deviationId} is a client-supplied string (e.g. {@code DEV001}).
 * Enumerated fields ({@code relatedEntityType}, {@code impact}, {@code status})
 * are stored as plain strings to match the rest of the schema.
 */
@Entity
@Table(name = "deviation_record")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DeviationRecord {

    @Id
    @Column(name = "deviationId", length = 20)
    private String deviationId;

    /** Batch / Trial / Shipment */
    @Column(name = "relatedEntityType", nullable = false, length = 20)
    private String relatedEntityType;

    @Column(name = "relatedEntityId", nullable = false, length = 20)
    private String relatedEntityId;

    @Column(name = "description", length = 2000)
    private String description;

    @Column(name = "detectedById", length = 20)
    private String detectedById;

    @Column(name = "detectionDate")
    private LocalDate detectionDate;

    /** Critical / Major / Minor */
    @Column(name = "impact", length = 20)
    private String impact;

    /** Open / UnderInvestigation / CAPAAssigned / Closed */
    @Column(name = "status", length = 30)
    private String status;
}
