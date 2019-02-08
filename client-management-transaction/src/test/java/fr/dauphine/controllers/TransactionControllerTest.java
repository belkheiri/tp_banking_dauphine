/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.dauphine.controllers;

import fr.dauphine.accounts.dtos.OperationDTO;
import fr.dauphine.exceptions.AccountException;
import fr.dauphine.servicesI.ITransactionService;
import fr.dauphine.servicesImpl.TransactionService;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import static org.mockito.Matchers.any;
import org.mockito.Mockito;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 *
 * @author zouhairhajji
 */
@RunWith(MockitoJUnitRunner.class)
public class TransactionControllerTest {

    @InjectMocks
    private TransactionController transactionController;

    private ITransactionService transactionService;

    @Before
    public void before() {
        // init mocks
        this.transactionService = Mockito.mock(TransactionService.class);
        this.transactionController = new TransactionController();
        this.transactionController.setTransactionService(transactionService);
    }

    @Test
    public void makeExternalAccountDoesntExists() throws AccountException {
        Mockito.when(this.transactionService.externalTransaction(any(String.class), any(String.class), any(Long.class)))
                .thenThrow(AccountException.class);
        ResponseEntity<?> responseEntity = this.transactionController.makeExternalTransaction("", "", 100L);

        verify(this.transactionService, times(1)).externalTransaction(any(String.class), any(String.class), any(Long.class));
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    public void makeExternalAccountExists() throws AccountException {
        OperationDTO operationDTO = OperationDTO.builder().build();
        Mockito.when(this.transactionService.externalTransaction(any(String.class), any(String.class), any(Long.class)))
                .thenReturn(operationDTO);
        ResponseEntity<?> responseEntity = this.transactionController.makeExternalTransaction("iban_code", "iban_code", 100L);

        verify(this.transactionService, times(1)).externalTransaction(any(String.class), any(String.class), any(Long.class));
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(operationDTO, responseEntity.getBody());
    }

    @Test
    public void makeBasicAccountExists() throws AccountException {
        OperationDTO operationDTO = OperationDTO.builder().build();
        Mockito.when(this.transactionService.basicTransaction(any(String.class), any(Long.class)))
                .thenReturn(operationDTO);
        
        
        ResponseEntity<?> responseEntity = this.transactionController.makeBasicTransaction("iban_code", 100L);

        verify(this.transactionService, times(1)).basicTransaction(any(String.class), any(Long.class));
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(operationDTO, responseEntity.getBody());
    }
    
    @Test
    public void makeBasicAccountDoesntExists() throws AccountException {
        Mockito.when(this.transactionService.basicTransaction(any(String.class), any(Long.class)))
                .thenThrow(AccountException.class);
        
        
        ResponseEntity<?> responseEntity = this.transactionController.makeBasicTransaction("iban_code", 100L);

        verify(this.transactionService, times(1)).basicTransaction(any(String.class), any(Long.class));
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    
    @Test
    public void findAllTransactionOfAccountDoesntExists() throws AccountException {
        Mockito.when(this.transactionService.findTransactionsByIban(any(String.class)))
                .thenThrow(AccountException.class);
        
        ResponseEntity<?> responseEntity = this.transactionController.findAllTransactionsByIban("iban_code");

        verify(this.transactionService, times(1)).findTransactionsByIban(any(String.class));
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }
    
    @Test
    public void findAllTransactionOfAccountExists() throws AccountException {
        List<OperationDTO> operations = new ArrayList<>();
        Mockito.when(this.transactionService.findTransactionsByIban(any(String.class)))
                .thenReturn(operations);
        
        ResponseEntity<?> responseEntity = this.transactionController.findAllTransactionsByIban("iban_code");

        verify(this.transactionService, times(1)).findTransactionsByIban(any(String.class));
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(operations, responseEntity.getBody());
    }
    
    
    @Test
    public void findTransactionNotExists() throws AccountException {
        Mockito.when(this.transactionService.findTransactionById(any(Long.class)))
                .thenReturn(Optional.empty());
        
        ResponseEntity<?> responseEntity = this.transactionController.findTransactionById(10L);

        verify(this.transactionService, times(1)).findTransactionById(any(Long.class));
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }
    
    @Test
    public void findTransactionExists() throws AccountException {
        Optional<OperationDTO> op = Optional.of(OperationDTO.builder().build());
        Mockito.when(this.transactionService.findTransactionById(any(Long.class)))
                .thenReturn(op);
        
        ResponseEntity<?> responseEntity = this.transactionController.findTransactionById(10L);

        verify(this.transactionService, times(1)).findTransactionById(any(Long.class));
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(op, responseEntity.getBody());
    }
    
    
    @Test
    public void depositMoneyToAccountExists() throws AccountException {
        OperationDTO operationDTO = OperationDTO.builder().build();
        Mockito.when(this.transactionService.depositMoney(any(String.class), any(Long.class)))
                .thenReturn(operationDTO);
        
        ResponseEntity<?> responseEntity = this.transactionController.depositMoney("iban_code", 10L);

        verify(this.transactionService, times(1)).depositMoney(any(String.class), any(Long.class));
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(operationDTO, responseEntity.getBody());
    }
    
    @Test
    public void depositMoneyToAccountDoesntExists() throws AccountException {
        Mockito.when(this.transactionService.depositMoney(any(String.class), any(Long.class)))
                .thenThrow(AccountException.class);
        
        ResponseEntity<?> responseEntity = this.transactionController.depositMoney("iban_code", 10L);

        verify(this.transactionService, times(1)).depositMoney(any(String.class), any(Long.class));
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }
    
}
