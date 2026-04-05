package com.finance.dashboard.record.mapper;

import com.finance.dashboard.record.dto.FinancialRecordResponse;
import com.finance.dashboard.record.entity.FinancialRecord;
import org.springframework.stereotype.Component;

@Component
public class FinancialRecordMapper {

    public FinancialRecordResponse toResponse(FinancialRecord record) {
        return FinancialRecordResponse.builder()
                .id(record.getId())
                .amount(record.getAmount())
                .type(record.getType())
                .category(record.getCategory())
                .recordDate(record.getRecordDate())
                .notes(record.getNotes())
                .createdById(record.getCreatedBy().getId())
                .createdByName(record.getCreatedBy().getName())
                .createdAt(record.getCreatedAt())
                .updatedAt(record.getUpdatedAt())
                .build();
    }
}
