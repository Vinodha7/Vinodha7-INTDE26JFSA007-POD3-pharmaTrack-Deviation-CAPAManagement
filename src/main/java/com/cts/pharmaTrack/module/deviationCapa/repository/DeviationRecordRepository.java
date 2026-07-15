package com.cts.pharmaTrack.module.deviationCapa.repository;

import com.cts.pharmaTrack.module.deviationCapa.entity.DeviationRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeviationRecordRepository extends JpaRepository<DeviationRecord, String> {

    List<DeviationRecord> findByStatus(String status);

    List<DeviationRecord> findByRelatedEntityTypeAndRelatedEntityId(String relatedEntityType,
                                                                    String relatedEntityId);
}
