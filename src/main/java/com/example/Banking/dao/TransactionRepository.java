package com.example.Banking.dao;

import com.example.Banking.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByUserId(Long accountId);
    List<Transaction> findByUserIdAndTransactionTimeBetween(Long accountId, Date start, Date end);
}
