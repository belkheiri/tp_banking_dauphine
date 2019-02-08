/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.dauphine.controllers;

import fr.dauphine.accounts.dtos.AccountDTO;
import fr.dauphine.accounts.dtos.AccountDetailDTO;
import fr.dauphine.accounts.enums.AccountType;
import fr.dauphine.exceptions.AccountException;
import fr.dauphine.servicesI.IAccountService;
import java.util.List;
import lombok.Setter;
import org.iban4j.CountryCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author jihane and zouhair
 */
@RestController
@RequestMapping("accounts")
@Setter
public class AccountController {

    @Autowired
    private IAccountService accountService;

    
    @PutMapping("/get_account_details")
    public ResponseEntity<?> getAccountDetails(
            @RequestParam(name = "iban", required = true) String iban) {
        try {
            AccountDetailDTO accountDetailDTO = this.accountService.getAccountDetail(iban);
            return new ResponseEntity(accountDetailDTO, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
    
    @PutMapping("/create_account")
    public AccountDTO createAccount(
            @RequestParam(name = "account_type", required = true) AccountType accountType,
            @RequestParam(name = "account_fee", required = true) Long accountFee,
            @RequestParam(name = "interest", required = true) Long interest) {

        return this.accountService.createAccount(CountryCode.FR, accountType, accountFee, interest);
    }
    
    @PostMapping("/update_account")
    public ResponseEntity<?> updateAccount(
            @RequestParam(name = "iban", required = true) String iban,
            @RequestParam(name = "account_fee", required = true) Long accountFee,
            @RequestParam(name = "interest", required = true) Long interest) {
        try {
            AccountDTO accountDTO = this.accountService.updateAccount(iban, accountFee, interest);
            return new ResponseEntity(accountDTO, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
    
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public List<AccountDTO> findAllAccounts() {
        return this.accountService.findAll();
    }
    
    
    @GetMapping("/check_account_exists")
    public Boolean checkWhetherAccountExistsOrNo(
            @RequestParam(name = "iban", required = true) String iban) {
        try {
            return this.accountService.findAccountByIban(iban) != null;
        } catch (AccountException ex) {
            return Boolean.FALSE;
        }
    }
    
    @GetMapping("/find_by_iban")
    public AccountDTO findAccountByIban(
            @RequestParam(name = "iban", required = true) String iban) throws AccountException {

        return this.accountService.findAccountByIban(iban);
    }

    @PutMapping("update_balance")
    public AccountDTO updateBalanceByIban(
            @RequestParam(name = "iban", required = true) String iban,
            @RequestParam(name = "amount", required = true) Long amount) throws AccountException {

        return this.accountService.updateAccountBalanceByIban(iban, amount);
    }

}
