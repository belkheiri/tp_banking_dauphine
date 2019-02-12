package fr.dauphine.remotes;

import fr.dauphine.accounts.dtos.AccountDTO;
import fr.dauphine.exceptions.AccountException;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.FeignClientProperties.FeignClientConfiguration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author jihane and zouhair
 */
@FeignClient(name = "service-account", configuration = FeignClientConfiguration.class)
public interface RemoteAccountAPI {
    
    @GetMapping("/accounts/find_by_iban")
    public AccountDTO findAccountByIban(
            @RequestParam(name = "iban", required = true) String iban) throws AccountException ;

    @PutMapping("/accounts/update_balance")
    public AccountDTO updateBalanceByIban(
            @RequestParam(name = "iban", required = true) String iban,
            @RequestParam(name = "amount", required = true) Long amount) throws AccountException ;
    
    @GetMapping("/accounts/check_account_exists")
    public Boolean checkWhetherAccountExistsOrNo(
            @RequestParam(name = "iban", required = true) String iban);
    
}
