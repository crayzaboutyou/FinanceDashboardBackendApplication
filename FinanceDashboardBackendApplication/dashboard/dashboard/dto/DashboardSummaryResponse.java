package com.finance.dashboard.dashboard.dto;

import com.finance.dashboard.record.dto.FinancialRecordResponse;
import java.math.BigDecimal;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DashboardSummaryResponse {

    private BigDecimal totalIncome;
    private BigDecimal totalExpense;
    private BigDecimal netBalance;
    private List<CategorySummaryResponse> categoryTotals;
    private List<FinancialRecordResponse> recentActivity;
    private List<TrendPointResponse> monthlyTrends;
}
