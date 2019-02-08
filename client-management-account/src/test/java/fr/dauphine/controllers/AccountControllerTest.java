/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.dauphine.controllers;

import fr.dauphine.accounts.dtos.AccountDTO;
import fr.dauphine.exceptions.AccountException;
import fr.dauphine.servicesI.IAccountService;
import fr.dauphine.servicesImpl.AccountService;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import static org.mockito.Matchers.any;
import org.mockito.Mockito;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 *
 * @author zouhairhajji
 */
@RunWith(MockitoJUnitRunner.class)
public class AccountControllerTest {

    @InjectMocks
    private AccountController accountController;

    private IAccountService accountService;

    @Before
    public void before() {
        // init mocks
        this.accountService = Mockito.mock(AccountService.class);
        this.accountController = new AccountController();
        this.accountController.setAccountService(accountService);
    }

    @Test
    public void testUpdateAccountDoesntExists() throws AccountException {
        when(this.accountService.updateAccount(any(String.class), any(Long.class), any(Long.class)))
                .thenThrow(AccountException.class);
        ResponseEntity<?> responseEntity = this.accountController.updateAccount("iban", 10L, 10L);
        verify(this.accountService, times(1)).updateAccount("iban", 10L, 10L);
        Assert.assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    public void testUpdateAccountExists() throws AccountException {
        AccountDTO accountDTO = AccountDTO.builder().build();
        when(this.accountService.updateAccount(any(String.class), any(Long.class), any(Long.class)))
                .thenReturn(accountDTO);
        ResponseEntity<?> responseEntity = this.accountController.updateAccount("iban", 10L, 10L);
        verify(this.accountService, times(1)).updateAccount("iban", 10L, 10L);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assert.assertEquals(accountDTO, responseEntity.getBody());
    }

    @Test
    public void testFindAllAccounts() throws AccountException {
        List<AccountDTO> accounts = new ArrayList<AccountDTO>() {
            {
                add(AccountDTO.builder().build());
                add(AccountDTO.builder().build());
            }
        };
        when(this.accountService.findAll())
                .thenReturn(accounts);
        List<AccountDTO> accountDTOs = this.accountController.findAllAccounts();
        verify(this.accountService, times(1)).findAll();
        Assert.assertEquals(accounts, accountDTOs);
    }

    @Test
    public void checkExistanceOfAccountExists() throws AccountException {
        AccountDTO accountDTO = AccountDTO.builder().build();
        when(this.accountService.findAccountByIban(any(String.class)))
                .thenReturn(accountDTO);
        
        Boolean existance = this.accountController.checkWhetherAccountExistsOrNo("iban_code");
        verify(this.accountService, times(1)).findAccountByIban(any(String.class));
        Assert.assertEquals(Boolean.TRUE, existance);
    }

    @Test
    public void checkExistanceOfAccountDoesntExists() throws AccountException {
        when(this.accountService.findAccountByIban(any(String.class)))
                .thenThrow(AccountException.class);
        
        Boolean existance = this.accountController.checkWhetherAccountExistsOrNo("iban_code");
        verify(this.accountService, times(1)).findAccountByIban(any(String.class));
        Assert.assertEquals(Boolean.FALSE, existance);
    }
    
}
