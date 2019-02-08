/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.dauphine.servicesImpl;

import fr.dauphine.accounts.dtos.OperationDTO;
import fr.dauphine.entities.TransactionEntity;
import fr.dauphine.exceptions.AccountException;
import fr.dauphine.remotes.RemoteAccountAPI;
import fr.dauphine.repositories.TransactionRepository;
import java.util.Optional;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Matchers.any;
import org.mockito.Mockito;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import org.modelmapper.ModelMapper;

/**
 *
 * @author zouhairhajji
 */
public class TransactionServiceTest {

    private TransactionService transactionService;

    private TransactionRepository transactionRepository;
    private RemoteAccountAPI accountAPI;
    private ModelMapper mapper;

    @Before
    public void beforeTest() {
        this.transactionService = new TransactionService();

        this.accountAPI = Mockito.mock(RemoteAccountAPI.class);
        this.mapper = Mockito.mock(ModelMapper.class);
        this.transactionRepository = Mockito.mock(TransactionRepository.class);

        this.transactionService.setAccountAPI(accountAPI);
        this.transactionService.setMapper(mapper);
        this.transactionService.setTransactionRepository(transactionRepository);
    }

    @Test
    public void testMakeExternalTransactionIbanDestDoesntExists() {
        String ibanSource = "iban_source";
        String ibanFrom = "iban_dest";

        Mockito.when(this.accountAPI.checkWhetherAccountExistsOrNo(ibanSource)).thenReturn(Boolean.FALSE);
        Mockito.when(this.accountAPI.checkWhetherAccountExistsOrNo(ibanFrom)).thenReturn(Boolean.TRUE);

        try {
            this.transactionService.externalTransaction(ibanSource, ibanFrom, 10L);
            Assert.fail("the method should throw accountexception");
        } catch (AccountException ex) {
            Mockito.verify(this.transactionRepository, times(0)).save(any(TransactionEntity.class));
            Mockito.verify(this.accountAPI, times(1)).checkWhetherAccountExistsOrNo(any(String.class));
        }
    }

    @Test
    public void testMakeExternalTransactionIbansExists() {
        String ibanSource = "iban_source";
        String ibanFrom = "iban_dest";
        
        Mockito.when(this.accountAPI.checkWhetherAccountExistsOrNo(ibanSource)).thenReturn(Boolean.TRUE);
        Mockito.when(this.accountAPI.checkWhetherAccountExistsOrNo(ibanFrom)).thenReturn(Boolean.TRUE);

        try {
            this.transactionService.externalTransaction(ibanSource, ibanFrom, 10L);
            Mockito.verify(this.transactionRepository, times(1)).save(any(TransactionEntity.class));
            Mockito.verify(this.accountAPI, times(2)).checkWhetherAccountExistsOrNo(any(String.class));
        } catch (AccountException ex) {
            Assert.fail("the method should not throw exception");
        }
    }

    //@Test
    public void testFindTransactionDoesntExists() {
        when(this.transactionRepository.findById(any(Long.class)))
                .thenReturn(Optional.empty());
        
        Optional<OperationDTO> result = this.transactionService.findTransactionById(10L);
        
        Mockito.verify(this.transactionRepository, times(1)).findById(any(Long.class));
        Assert.assertEquals(Optional.empty(), result);
    }



    
}
