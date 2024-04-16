package com.example.Banking.payload;

import java.math.BigDecimal;
import java.util.Date;

public class TransactionDto {
    private Long id;
    private Date transactionTime;
    private String description;
    private Integer refNumber;
    private String transactionType;
    private double amount;

    public TransactionDto(Long id, Date transactionTime, String description, Integer refNumber, String transactionType, double amount) {
        this.id = id;
        this.transactionTime = transactionTime;
        this.description = description;
        this.refNumber = refNumber;
        this.transactionType = transactionType;
        this.amount = amount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getTransactionTime() {
        return transactionTime;
    }

    public void setTransactionTime(Date transactionTime) {
        this.transactionTime = transactionTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getRefNumber() {
        return refNumber;
    }

    public void setRefNumber(Integer refNumber) {
        this.refNumber = refNumber;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public TransactionDto() {
    }
}
