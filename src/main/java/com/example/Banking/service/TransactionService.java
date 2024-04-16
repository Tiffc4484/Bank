package com.example.Banking.service;

import com.example.Banking.payload.TransactionDto;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface TransactionService {
    TransactionDto createTransaction(Long accountId, TransactionDto transactionDto);
    List<TransactionDto> getTransactionsByAccountId(Long accountId);
    TransactionDto getTransactionById(Long accountId, Long transactionId);

    List<TransactionDto> getTransactionByDateBetween(Long accountId, Date startDate, Date endDate);
    TransactionDto updateTransaction(Long accountId, Long transactionId, TransactionDto transactionDto);
    void deleteTransaction(Long accountId, Long transactionId);
}
