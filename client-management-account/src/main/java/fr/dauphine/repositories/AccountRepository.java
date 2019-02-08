/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.dauphine.repositories;

import fr.dauphine.entities.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 *
 * @author jihane and zouhair
 */
public interface AccountRepository extends JpaRepository<AccountEntity, String>{
    
    @Modifying
    @Query("UPDATE AccountEntity ae SET ae.balance = ae.balance + ?2 WHERE ae.iban = ?1")
    public void addAmountByIban(String iban, Long amount);
    
    
}
