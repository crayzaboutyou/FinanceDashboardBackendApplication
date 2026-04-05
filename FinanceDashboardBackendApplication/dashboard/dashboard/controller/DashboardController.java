package com.finance.dashboard.dashboard.controller;

import com.finance.dashboard.common.dto.ApiResponse;
import com.finance.dashboard.dashboard.dto.DashboardSummaryResponse;
import com.finance.dashboard.dashboard.service.DashboardService;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping("/summary")
    @PreAuthorize("hasAnyRole('ADMIN', 'ANALYST', 'VIEWER')")
    public ResponseEntity<ApiResponse<DashboardSummaryResponse>> getDashboardSummary(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return ResponseEntity.ok(ApiResponse.<DashboardSummaryResponse>builder()
                .success(true)
                .message("Dashboard summary fetched successfully")
                .data(dashboardService.getDashboardSummary(startDate, endDate))
                .build());
    }
}
