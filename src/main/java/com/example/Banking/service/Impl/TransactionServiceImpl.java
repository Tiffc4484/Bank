package com.example.Banking.service.Impl;

import com.example.Banking.dao.TransactionRepository;
import com.example.Banking.dao.UserRepository;
import com.example.Banking.entity.Transaction;
import com.example.Banking.entity.TransactionType;
import com.example.Banking.entity.User;
import com.example.Banking.exception.BankAPIException;
import com.example.Banking.exception.ResourceNotFoundException;
import com.example.Banking.payload.TransactionDto;
import com.example.Banking.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public TransactionDto createTransaction(Long accountId, TransactionDto transactionDto) {
        Transaction transaction = mapToEntity(transactionDto);
        User user = userRepository.findById(accountId).orElseThrow(() -> new ResourceNotFoundException("User", "id", accountId));

        double updatedBalance = transactionDto.getTransactionType().equals("WITHDRAW") ?  user.getBalance() - transactionDto.getAmount() : user.getBalance() + transactionDto.getAmount();

        transaction.setUser(user);
        user.setBalance(updatedBalance);
        userRepository.save(user);
        Transaction savedTransaction = transactionRepository.save(transaction);

        return mapToDto(savedTransaction);
    }

    @Override
    public List<TransactionDto> getTransactionsByAccountId(Long accountId) {
        List<Transaction> transactions = transactionRepository.findByUserId(accountId);

        return transactions.stream().map(transaction -> mapToDto(transaction)).collect(Collectors.toList());
    }

    @Override
    public TransactionDto getTransactionById(Long accountId, Long transactionId) {
        User user = userRepository.findById(accountId).orElseThrow(() -> new ResourceNotFoundException("account", "id", accountId));

        Transaction transaction = transactionRepository.findById(transactionId).orElseThrow(() -> new ResourceNotFoundException("transaction", "id", transactionId));

        if (!transaction.getUser().getId().equals(user.getId())) {
            throw new BankAPIException();
        }

        return mapToDto(transaction);
    }

    @Override
    public List<TransactionDto> getTransactionByDateBetween(Long accountId, Date startDate, Date endDate) {
        List<Transaction> transactions = transactionRepository.findByUserIdAndTransactionTimeBetween(accountId, startDate, endDate);

        return transactions.stream().map(transaction -> mapToDto(transaction)).collect(Collectors.toList());
    }

    @Override
    public TransactionDto updateTransaction(Long accountId, Long transactionId, TransactionDto transactionDto) {
        User user = userRepository.findById(accountId).orElseThrow(() -> new ResourceNotFoundException("account", "id", accountId));

        Transaction transaction = transactionRepository.findById(transactionId).orElseThrow(() -> new ResourceNotFoundException("transaction", "id", transactionId));

        if (!transaction.getUser().getId().equals(user.getId())) {
            throw new BankAPIException();
        }

        transaction.setTransactionTime(transactionDto.getTransactionTime());
        if (transactionDto.getTransactionType().equals("DEPOSIT")) {
            transaction.setTransactionType(TransactionType.DEPOSIT);
        } else {
            transaction.setTransactionType(TransactionType.WITHDRAW);
        }
        transaction.setAmount(transactionDto.getAmount());
        transaction.setDescription(transactionDto.getDescription());
        transaction.setRefNumber(transactionDto.getRefNumber());

        Transaction savedTransaction = transactionRepository.save(transaction);

        return mapToDto(savedTransaction);
    }

    @Override
    public void deleteTransaction(Long accountId, Long transactionId) {
        User user = userRepository.findById(accountId).orElseThrow(() -> new ResourceNotFoundException("account", "id", accountId));

        Transaction transaction = transactionRepository.findById(transactionId).orElseThrow(() -> new ResourceNotFoundException("transaction", "id", transactionId));

        if (!transaction.getUser().getId().equals(user.getId())) {
            throw new BankAPIException();
        }

        double updatedBalance = user.getBalance() - transaction.getAmount();
        user.setBalance(updatedBalance);

        transactionRepository.delete(transaction);
        userRepository.save(user);
    }

    public Transaction mapToEntity(TransactionDto transactionDto) {
        Transaction transaction = new Transaction();

        transaction.setId(transaction.getId());
        transaction.setTransactionTime(transactionDto.getTransactionTime());
        if (transactionDto.getTransactionType().equals("DEPOSIT")) {
            transaction.setTransactionType(TransactionType.DEPOSIT);
        } else {
            transaction.setTransactionType(TransactionType.WITHDRAW);
        }
        transaction.setDescription(transactionDto.getDescription());
        transaction.setAmount(transactionDto.getAmount());
        transaction.setRefNumber(transactionDto.getRefNumber());

        return transaction;
    }

    public TransactionDto mapToDto(Transaction transaction) {
        TransactionDto transactionDto = new TransactionDto();

        transactionDto.setId(transaction.getId());
        transactionDto.setDescription(transaction.getDescription());
        transactionDto.setAmount(transaction.getAmount());
        if (transaction.getTransactionType() == TransactionType.DEPOSIT) {
            transactionDto.setTransactionType("DEPOSIT");
        } else {
            transactionDto.setTransactionType("WITHDRAW");
        }
        transactionDto.setRefNumber(transaction.getRefNumber());
        transactionDto.setTransactionTime(transaction.getTransactionTime());

        return transactionDto;
    }
}
