/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.dauphine.servicesI;

import fr.dauphine.accounts.dtos.AccountDTO;
import fr.dauphine.accounts.dtos.AccountDetailDTO;
import fr.dauphine.accounts.enums.AccountType;
import fr.dauphine.exceptions.AccountException;
import java.util.List;
import org.iban4j.CountryCode;

/**
 *
 * @author jihane and zouhair
 */
public interface IAccountService {

    public List<AccountDTO> findAll();
    
    public AccountDetailDTO getAccountDetail(String iban) throws AccountException;
    
    public boolean checkAccountExists(String iban);

    public AccountDTO findAccountByIban(String iban) throws AccountException;

    public AccountDTO createAccount(CountryCode countryCode, AccountType accountType, Long accountFee, Long interest);

    public AccountDTO updateAccount(String iban, Long accoutFee, Long interest) throws AccountException;
    
    public AccountDTO updateAccountBalanceByIban(String iban, Long amount) throws AccountException ;

}
