/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.dauphine.controllers;

import fr.dauphine.accounts.dtos.OperationDTO;
import fr.dauphine.accounts.enums.TransactionType;
import fr.dauphine.exceptions.AccountException;
import fr.dauphine.servicesI.ITransactionService;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author jihane and zouhair
 */
@Setter
@RestController
@RequestMapping("transactions")
public class TransactionController {

    @Autowired
    private ITransactionService transactionService;

    @PostMapping("make_external_transaction")
    public ResponseEntity<?> makeExternalTransaction(
            @RequestParam(name = "iban_sender", required = true) String ibanSender,
            @RequestParam(name = "iban_receiver", required = true) String ibanReceiver,
            @RequestParam(name = "amount", required = true) Long amount) {
        try {
            OperationDTO operationDTO = this.transactionService.externalTransaction(ibanSender, ibanReceiver, amount);
            return new ResponseEntity<>(operationDTO, HttpStatus.OK);
        } catch (AccountException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("make_internal_transaction")
    public ResponseEntity<?> makeBasicTransaction(
            @RequestParam(name = "iban_sender", required = true) String ibanSender,
            @RequestParam(name = "amount", required = true) Long amount) {
        try {
            OperationDTO operationDTO = this.transactionService.basicTransaction(ibanSender, amount);
            return new ResponseEntity<>(operationDTO, HttpStatus.OK);
        } catch (AccountException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("find_transactions_by_iban")
    public ResponseEntity<?> findAllTransactionsByIban(
            @RequestParam(name = "iban", required = true) String iban) {
        try {
            List<OperationDTO> operations = this.transactionService.findTransactionsByIban(iban);
            return new ResponseEntity<>(operations, HttpStatus.OK);
        } catch (AccountException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("find_transaction_by_id")
    public ResponseEntity<?> findTransactionById(
            @RequestParam(name = "transaction_id", required = true) Long transactionId) {
        Optional<OperationDTO> optOperation = this.transactionService.findTransactionById(transactionId);
        if (optOperation.isPresent()) {
            return new ResponseEntity<>(optOperation, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("search_by_iban_and_type")
    public ResponseEntity<?> searchTransactionsByIbanAndType(
            @RequestParam(name = "iban", required = true) String iban,
            @RequestParam(name = "type", required = true) TransactionType transactionType) {
        try {
            List<OperationDTO> operations = this.transactionService.searchTransactionsByIbanAndType(iban, transactionType);
            return new ResponseEntity<>(operations, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.GATEWAY_TIMEOUT);
        }
    }

    @PutMapping("deposit_money")
    public ResponseEntity<?> depositMoney(
            @RequestParam(name = "iban", required = true) String iban,
            @RequestParam(name = "amount", required = true) Long amount) {
        try {
            OperationDTO operation = this.transactionService.depositMoney(iban, amount);
            return new ResponseEntity<>(operation, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
    
    @GetMapping("search_by_iban_between_dates")
    public ResponseEntity<?> searchTransactionsByIbanAndType(
            @RequestParam(name = "iban", required = true) String iban,
            @RequestParam(name = "start_date", required = true) Timestamp startDate,
            @RequestParam(name = "end_date", required = true) Timestamp endDate) {
        try {
            List<OperationDTO> operations = this.transactionService.searchTransactionsByIbanBetweenDates(iban, startDate, endDate);
            return new ResponseEntity<>(operations, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.GATEWAY_TIMEOUT);
        }
    }

}
