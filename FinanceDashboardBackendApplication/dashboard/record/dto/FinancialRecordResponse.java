package com.finance.dashboard.record.dto;

import com.finance.dashboard.record.enums.RecordType;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FinancialRecordResponse {

    private Long id;
    private BigDecimal amount;
    private RecordType type;
    private String category;
    private LocalDate recordDate;
    private String notes;
    private Long createdById;
    private String createdByName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
