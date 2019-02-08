/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.dauphine.servicesI;

import fr.dauphine.accounts.dtos.OperationDTO;
import fr.dauphine.accounts.enums.TransactionType;
import fr.dauphine.exceptions.AccountException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;


/**
 *
 * @author jihane and zouhair
 */
public interface ITransactionService {

    public OperationDTO externalTransaction(String iban, String ibanDest, Long amount) throws AccountException;
    
    public OperationDTO basicTransaction(String iban, Long amount) throws AccountException;
    
    public List<OperationDTO> findTransactionsByIban(String iban) throws AccountException;
    
    public List<OperationDTO> searchTransactionsByIbanAndType(String iban, TransactionType transactionType) throws AccountException;
    
    public List<OperationDTO> searchTransactionsByIbanBetweenDates(String iban, Timestamp startDate, Timestamp endDate) throws AccountException;
    
    public OperationDTO depositMoney(String iban, Long amount) throws AccountException;
    
    
    public Optional<OperationDTO> findTransactionById(Long transaction);
    
    
}
