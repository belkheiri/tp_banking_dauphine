/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.dauphine.entities;


import fr.dauphine.accounts.enums.AccountType;
import java.io.Serializable;
import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
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
@Table(name = "accounts")
@Entity()
public class AccountEntity implements Serializable{
    
    @Id
    @Column(name = "iban")
    private String iban;
    
    
    @Column(name = "balance")
    private Long balance;
    
    @Column(name = "interest")
    private Long interest;
    
    @Column(name = "account_fee")
    private Long accountFee;
    
    @Column(name = "account_type")
    private AccountType accountType;
    
    @Column(name = "creation_date")
    private Timestamp creationDate;
    
    
}
