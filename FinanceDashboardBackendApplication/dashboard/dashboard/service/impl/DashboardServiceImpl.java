package com.finance.dashboard.dashboard.service.impl;

import com.finance.dashboard.dashboard.dto.CategorySummaryResponse;
import com.finance.dashboard.dashboard.dto.DashboardSummaryResponse;
import com.finance.dashboard.dashboard.dto.TrendPointResponse;
import com.finance.dashboard.dashboard.service.DashboardService;
import com.finance.dashboard.record.enums.RecordType;
import com.finance.dashboard.record.mapper.FinancialRecordMapper;
import com.finance.dashboard.record.repository.FinancialRecordRepository;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DashboardServiceImpl implements DashboardService {

    private final FinancialRecordRepository financialRecordRepository;
    private final FinancialRecordMapper financialRecordMapper;

    @Override
    @Transactional(readOnly = true)
    public DashboardSummaryResponse getDashboardSummary(LocalDate startDate, LocalDate endDate) {
        BigDecimal totalIncome = financialRecordRepository.sumByTypeAndDateRange(RecordType.INCOME, startDate, endDate);
        BigDecimal totalExpense = financialRecordRepository.sumByTypeAndDateRange(RecordType.EXPENSE, startDate, endDate);

        List<CategorySummaryResponse> categoryTotals = financialRecordRepository.sumByCategory(startDate, endDate).stream()
                .map(row -> new CategorySummaryResponse((String) row[0], (BigDecimal) row[1]))
                .toList();

        return DashboardSummaryResponse.builder()
                .totalIncome(totalIncome)
                .totalExpense(totalExpense)
                .netBalance(totalIncome.subtract(totalExpense))
                .categoryTotals(categoryTotals)
                .recentActivity(financialRecordRepository.findTop5ByOrderByRecordDateDescCreatedAtDesc().stream()
                        .map(financialRecordMapper::toResponse)
                        .toList())
                .monthlyTrends(buildMonthlyTrends(startDate, endDate))
                .build();
    }

    private List<TrendPointResponse> buildMonthlyTrends(LocalDate startDate, LocalDate endDate) {
        Map<YearMonth, BigDecimal> incomeMap = new LinkedHashMap<>();
        Map<YearMonth, BigDecimal> expenseMap = new LinkedHashMap<>();

        for (Object[] row : financialRecordRepository.monthlyTrend(startDate, endDate)) {
            int year = ((Number) row[0]).intValue();
            int month = ((Number) row[1]).intValue();
            RecordType type = (RecordType) row[2];
            BigDecimal amount = (BigDecimal) row[3];
            YearMonth yearMonth = YearMonth.of(year, month);

            if (type == RecordType.INCOME) {
                incomeMap.put(yearMonth, amount);
            } else {
                expenseMap.put(yearMonth, amount);
            }
        }

        List<TrendPointResponse> trends = new ArrayList<>();
        for (YearMonth yearMonth : unionKeys(incomeMap, expenseMap)) {
            BigDecimal income = incomeMap.getOrDefault(yearMonth, BigDecimal.ZERO);
            BigDecimal expense = expenseMap.getOrDefault(yearMonth, BigDecimal.ZERO);
            trends.add(new TrendPointResponse(yearMonth.toString(), income, expense, income.subtract(expense)));
        }
        return trends;
    }

    private List<YearMonth> unionKeys(Map<YearMonth, BigDecimal> incomeMap, Map<YearMonth, BigDecimal> expenseMap) {
        LinkedHashMap<YearMonth, Boolean> merged = new LinkedHashMap<>();
        incomeMap.keySet().forEach(key -> merged.put(key, Boolean.TRUE));
        expenseMap.keySet().forEach(key -> merged.put(key, Boolean.TRUE));
        return new ArrayList<>(merged.keySet());
    }
}
