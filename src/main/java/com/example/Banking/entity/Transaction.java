package com.example.Banking.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(
        name = "transactions"
)
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double amount;

    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

    @CreationTimestamp
    private Date transactionTime;

    private String description;

    private Integer refNumber;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Integer getRefNumber() {
        return refNumber;
    }

    public void setRefNumber(Integer refNumber) {
        this.refNumber = refNumber;
    }


    public Transaction(Long amount, TransactionType transactionType, Date transactionTime, String description, Integer refNumber, User user) {
        this.amount = amount;
        this.transactionType = transactionType;
        this.transactionTime = transactionTime;
        this.description = description;
        this.refNumber = refNumber;
        this.user = user;
    }

    public Transaction() {

    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType type) {
        this.transactionType = type;
    }

    public Date getTransactionTime() {
        return transactionTime;
    }

    public void setTransactionTime(Date date) {
        this.transactionTime = date;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User person) {
        this.user = person;
    }
}
