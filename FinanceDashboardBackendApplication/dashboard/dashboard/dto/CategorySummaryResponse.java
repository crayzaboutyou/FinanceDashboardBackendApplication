package com.finance.dashboard.dashboard.dto;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CategorySummaryResponse {

    private final String category;
    private final BigDecimal total;
}
