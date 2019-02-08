/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.dauphine.repositories;

import fr.dauphine.accounts.enums.TransactionType;
import fr.dauphine.entities.TransactionEntity;
import java.sql.Timestamp;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 *
 * @author jihane and zouhair
 */
public interface TransactionRepository extends JpaRepository<TransactionEntity, Long> {

    @Query("    SELECT te "
            + " FROM TransactionEntity te"
            + " WHERE 1=1"
            + "     AND te.ibanSource = ?1"
            + "     OR te.ibanDestination = ?1 ")
    public List<TransactionEntity> findTransactionsByIBan(String iban);
    
    @Query("    SELECT te "
            + " FROM TransactionEntity te"
            + " WHERE 1=1"
            + "     AND te.transactionType = ?2"
            + "     AND (  "
            + "         te.ibanSource = ?1"
            + "        OR "
            + "         te.ibanDestination = ?1 "
            + "     ) ")
    public List<TransactionEntity> findTransactionsByIBanAndType(String iban, TransactionType transactionType);


    @Query("    SELECT te "
            + " FROM TransactionEntity te"
            + " WHERE 1=1 "
            + "    AND te.transactionDate >= ?2 "
            + "    AND te.transactionDate <= ?3 "
            + "    AND (  "
            + "         te.ibanSource = ?1"
            + "        OR "
            + "         te.ibanDestination = ?1 "
            + "     ) ")
    public List<TransactionEntity> findTransactionsByIBanBetweenDates(String iban, Timestamp startDate, Timestamp endDate);

}
