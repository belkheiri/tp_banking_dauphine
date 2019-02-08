/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.dauphine.entities;

import fr.dauphine.accounts.enums.TransactionType;
import java.io.Serializable;
import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author jihane and zouhair
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "transactins")
@Entity
public class TransactionEntity implements Serializable{
 
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column( name = "ID_TRANS")
    private Long idTransaction;
    
    @Column( name = "IBAN_SOURCE")
    private String ibanSource;
    
    @Column( name = "IBAN_DESTINATION")
    private String ibanDestination;
    
    @Column( name = "TRANS_TYPE")
    private TransactionType transactionType;
    
    @Column( name = "AMOUNT")
    private Long amount;
    
    @Column( name = "CREATION_DATE")
    private Timestamp transactionDate;
    
}
