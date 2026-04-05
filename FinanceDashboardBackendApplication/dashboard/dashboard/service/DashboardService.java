package com.finance.dashboard.dashboard.service;

import com.finance.dashboard.dashboard.dto.DashboardSummaryResponse;
import java.time.LocalDate;

public interface DashboardService {

    DashboardSummaryResponse getDashboardSummary(LocalDate startDate, LocalDate endDate);
}
