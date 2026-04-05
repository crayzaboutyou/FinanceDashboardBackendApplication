package com.finance.dashboard.record.service;

import com.finance.dashboard.record.dto.CreateFinancialRecordRequest;
import com.finance.dashboard.record.dto.FinancialRecordResponse;
import com.finance.dashboard.record.dto.UpdateFinancialRecordRequest;
import com.finance.dashboard.record.enums.RecordType;
import java.time.LocalDate;
import java.util.List;

public interface FinancialRecordService {

    FinancialRecordResponse createRecord(CreateFinancialRecordRequest request, String userEmail);

    List<FinancialRecordResponse> getRecords(RecordType type, String category, LocalDate startDate, LocalDate endDate);

    FinancialRecordResponse getRecordById(Long id);

    FinancialRecordResponse updateRecord(Long id, UpdateFinancialRecordRequest request);

    void deleteRecord(Long id);
}
