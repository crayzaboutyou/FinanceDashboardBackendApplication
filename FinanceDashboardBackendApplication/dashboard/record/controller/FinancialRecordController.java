package com.finance.dashboard.record.controller;

import com.finance.dashboard.common.dto.ApiResponse;
import com.finance.dashboard.record.dto.CreateFinancialRecordRequest;
import com.finance.dashboard.record.dto.FinancialRecordResponse;
import com.finance.dashboard.record.dto.UpdateFinancialRecordRequest;
import com.finance.dashboard.record.enums.RecordType;
import com.finance.dashboard.record.service.FinancialRecordService;
import jakarta.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/records")
@RequiredArgsConstructor
public class FinancialRecordController {

    private final FinancialRecordService financialRecordService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<FinancialRecordResponse>> createRecord(@Valid @RequestBody CreateFinancialRecordRequest request,
                                                                             Authentication authentication) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.<FinancialRecordResponse>builder()
                .success(true)
                .message("Financial record created successfully")
                .data(financialRecordService.createRecord(request, authentication.getName()))
                .build());
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'ANALYST', 'VIEWER')")
    public ResponseEntity<ApiResponse<List<FinancialRecordResponse>>> getRecords(
            @RequestParam(required = false) RecordType type,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return ResponseEntity.ok(ApiResponse.<List<FinancialRecordResponse>>builder()
                .success(true)
                .message("Financial records fetched successfully")
                .data(financialRecordService.getRecords(type, category, startDate, endDate))
                .build());
    }

    @GetMapping("/{recordId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'ANALYST', 'VIEWER')")
    public ResponseEntity<ApiResponse<FinancialRecordResponse>> getRecordById(@PathVariable Long recordId) {
        return ResponseEntity.ok(ApiResponse.<FinancialRecordResponse>builder()
                .success(true)
                .message("Financial record fetched successfully")
                .data(financialRecordService.getRecordById(recordId))
                .build());
    }

    @PutMapping("/{recordId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<FinancialRecordResponse>> updateRecord(@PathVariable Long recordId,
                                                                             @Valid @RequestBody UpdateFinancialRecordRequest request) {
        return ResponseEntity.ok(ApiResponse.<FinancialRecordResponse>builder()
                .success(true)
                .message("Financial record updated successfully")
                .data(financialRecordService.updateRecord(recordId, request))
                .build());
    }

    @DeleteMapping("/{recordId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> deleteRecord(@PathVariable Long recordId) {
        financialRecordService.deleteRecord(recordId);
        return ResponseEntity.ok(ApiResponse.<Void>builder()
                .success(true)
                .message("Financial record deleted successfully")
                .data(null)
                .build());
    }
}
