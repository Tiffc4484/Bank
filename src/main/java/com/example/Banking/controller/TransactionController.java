package com.example.Banking.controller;

import com.example.Banking.payload.TransactionDto;
import com.example.Banking.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("/api/v1/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping("/{accountId}")
    public ResponseEntity<TransactionDto> createTransaction(@RequestBody TransactionDto transactionDto, @PathVariable(name = "accountId") long accountId) {
        TransactionDto response = transactionService.createTransaction(accountId, transactionDto);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{accountId}/all")
    public List<TransactionDto> getAllTransactionsById(@PathVariable(name = "accountId") long id) {
        return transactionService.getTransactionsByAccountId(id);
    }

    @GetMapping("/{accountId}")
    public List<TransactionDto> getAllTransactionsBetweenDates(@PathVariable(name = "accountId") long accountId,
                                                               @RequestParam("start_date") String startDate,
                                                               @RequestParam("end_date") String endDate) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-mm-dd", Locale.ENGLISH);
        Date startTime = formatter.parse(startDate);
        Date endTime = formatter.parse(endDate);

        List<TransactionDto> transactionDtos = transactionService.getTransactionByDateBetween(accountId, startTime, endTime);

        return transactionDtos;
    }

    @GetMapping("/{accountId}/transaction/{id}")
    public ResponseEntity<TransactionDto> getTransactionById(@PathVariable(name = "accountId") long accountId,
                                                             @PathVariable(name = "id") long transactionId) {
        TransactionDto transactionDto = transactionService.getTransactionById(accountId, transactionId);

        return new ResponseEntity<>(transactionDto, HttpStatus.OK);
    }

    @PutMapping("/{accountId}/transaction/{id}")
    public ResponseEntity<TransactionDto> updateTransaction(@PathVariable(name = "accountId") long accountId,
                                                            @PathVariable(name = "id") long transactionId,
                                                            @RequestBody TransactionDto transactionDto) {
        TransactionDto updateTransaction = transactionService.updateTransaction(accountId, transactionId, transactionDto);

        return new ResponseEntity<>(updateTransaction, HttpStatus.OK);
    }

    @DeleteMapping("/{accountId}/transaction/{id}")
    public ResponseEntity<String> deleteTransaction(@PathVariable(name = "accountId") long accountId,
                                                    @PathVariable(name = "id") long transactionId) {
        transactionService.deleteTransaction(accountId, transactionId);

        return new ResponseEntity<>("Transaction deleted.", HttpStatus.OK);
    }


}
