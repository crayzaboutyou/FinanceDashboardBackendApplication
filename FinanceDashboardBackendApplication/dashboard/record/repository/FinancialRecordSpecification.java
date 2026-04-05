package com.finance.dashboard.record.repository;

import com.finance.dashboard.record.entity.FinancialRecord;
import com.finance.dashboard.record.enums.RecordType;
import java.time.LocalDate;
import org.springframework.data.jpa.domain.Specification;

public final class FinancialRecordSpecification {

    private FinancialRecordSpecification() {
    }

    public static Specification<FinancialRecord> withFilters(RecordType type,
                                                             String category,
                                                             LocalDate startDate,
                                                             LocalDate endDate) {
        return (root, query, cb) -> {
            var predicate = cb.conjunction();

            if (type != null) {
                predicate = cb.and(predicate, cb.equal(root.get("type"), type));
            }
            if (category != null && !category.isBlank()) {
                predicate = cb.and(predicate, cb.equal(cb.lower(root.get("category")), category.toLowerCase()));
            }
            if (startDate != null) {
                predicate = cb.and(predicate, cb.greaterThanOrEqualTo(root.get("recordDate"), startDate));
            }
            if (endDate != null) {
                predicate = cb.and(predicate, cb.lessThanOrEqualTo(root.get("recordDate"), endDate));
            }

            query.orderBy(cb.desc(root.get("recordDate")), cb.desc(root.get("createdAt")));
            return predicate;
        };
    }
}
