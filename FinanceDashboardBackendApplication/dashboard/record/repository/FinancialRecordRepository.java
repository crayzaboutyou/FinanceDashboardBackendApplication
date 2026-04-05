package com.finance.dashboard.record.repository;

import com.finance.dashboard.record.entity.FinancialRecord;
import com.finance.dashboard.record.enums.RecordType;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FinancialRecordRepository extends JpaRepository<FinancialRecord, Long>, JpaSpecificationExecutor<FinancialRecord> {

    List<FinancialRecord> findTop5ByOrderByRecordDateDescCreatedAtDesc();

    @Query("""
            select coalesce(sum(fr.amount), 0)
            from FinancialRecord fr
            where fr.type = :type
              and (:startDate is null or fr.recordDate >= :startDate)
              and (:endDate is null or fr.recordDate <= :endDate)
            """)
    BigDecimal sumByTypeAndDateRange(@Param("type") RecordType type,
                                     @Param("startDate") LocalDate startDate,
                                     @Param("endDate") LocalDate endDate);

    @Query("""
            select fr.category, coalesce(sum(fr.amount), 0)
            from FinancialRecord fr
            where (:startDate is null or fr.recordDate >= :startDate)
              and (:endDate is null or fr.recordDate <= :endDate)
            group by fr.category
            order by fr.category asc
            """)
    List<Object[]> sumByCategory(@Param("startDate") LocalDate startDate,
                                 @Param("endDate") LocalDate endDate);

    @Query("""
            select year(fr.recordDate), month(fr.recordDate), fr.type, coalesce(sum(fr.amount), 0)
            from FinancialRecord fr
            where (:startDate is null or fr.recordDate >= :startDate)
              and (:endDate is null or fr.recordDate <= :endDate)
            group by year(fr.recordDate), month(fr.recordDate), fr.type
            order by year(fr.recordDate), month(fr.recordDate)
            """)
    List<Object[]> monthlyTrend(@Param("startDate") LocalDate startDate,
                                @Param("endDate") LocalDate endDate);
}
