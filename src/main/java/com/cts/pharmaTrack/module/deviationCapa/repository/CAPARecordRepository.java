package com.cts.pharmaTrack.module.deviationCapa.repository;

import com.cts.pharmaTrack.module.deviationCapa.entity.CAPARecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CAPARecordRepository extends JpaRepository<CAPARecord, String> {

    List<CAPARecord> findByDeviationId(String deviationId);

    List<CAPARecord> findByStatus(String status);

    boolean existsByDeviationId(String deviationId);
}
