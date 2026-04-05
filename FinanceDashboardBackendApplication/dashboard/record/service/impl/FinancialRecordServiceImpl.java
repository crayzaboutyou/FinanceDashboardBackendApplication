package com.finance.dashboard.record.service.impl;

import com.finance.dashboard.common.exception.ResourceNotFoundException;
import com.finance.dashboard.record.dto.CreateFinancialRecordRequest;
import com.finance.dashboard.record.dto.FinancialRecordResponse;
import com.finance.dashboard.record.dto.UpdateFinancialRecordRequest;
import com.finance.dashboard.record.entity.FinancialRecord;
import com.finance.dashboard.record.enums.RecordType;
import com.finance.dashboard.record.mapper.FinancialRecordMapper;
import com.finance.dashboard.record.repository.FinancialRecordRepository;
import com.finance.dashboard.record.repository.FinancialRecordSpecification;
import com.finance.dashboard.record.service.FinancialRecordService;
import com.finance.dashboard.user.entity.User;
import com.finance.dashboard.user.repository.UserRepository;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FinancialRecordServiceImpl implements FinancialRecordService {

    private final FinancialRecordRepository financialRecordRepository;
    private final UserRepository userRepository;
    private final FinancialRecordMapper financialRecordMapper;

    @Override
    @Transactional
    public FinancialRecordResponse createRecord(CreateFinancialRecordRequest request, String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + userEmail));

        FinancialRecord record = FinancialRecord.builder()
                .amount(request.getAmount())
                .type(request.getType())
                .category(request.getCategory())
                .recordDate(request.getRecordDate())
                .notes(request.getNotes())
                .createdBy(user)
                .build();

        return financialRecordMapper.toResponse(financialRecordRepository.save(record));
    }

    @Override
    @Transactional(readOnly = true)
    public List<FinancialRecordResponse> getRecords(RecordType type, String category, LocalDate startDate, LocalDate endDate) {
        return financialRecordRepository.findAll(FinancialRecordSpecification.withFilters(type, category, startDate, endDate)).stream()
                .map(financialRecordMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public FinancialRecordResponse getRecordById(Long id) {
        return financialRecordMapper.toResponse(findRecordById(id));
    }

    @Override
    @Transactional
    public FinancialRecordResponse updateRecord(Long id, UpdateFinancialRecordRequest request) {
        FinancialRecord record = findRecordById(id);
        record.setAmount(request.getAmount());
        record.setType(request.getType());
        record.setCategory(request.getCategory());
        record.setRecordDate(request.getRecordDate());
        record.setNotes(request.getNotes());
        return financialRecordMapper.toResponse(financialRecordRepository.save(record));
    }

    @Override
    @Transactional
    public void deleteRecord(Long id) {
        FinancialRecord record = findRecordById(id);
        financialRecordRepository.delete(record);
    }

    private FinancialRecord findRecordById(Long id) {
        return financialRecordRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Financial record not found with id: " + id));
    }
}
