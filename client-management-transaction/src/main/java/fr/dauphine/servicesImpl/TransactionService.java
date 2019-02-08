/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.dauphine.servicesImpl;

import fr.dauphine.accounts.dtos.AccountDTO;
import fr.dauphine.accounts.dtos.OperationDTO;
import fr.dauphine.accounts.enums.TransactionType;
import fr.dauphine.entities.TransactionEntity;
import fr.dauphine.exceptions.AccountException;
import fr.dauphine.remotes.RemoteAccountAPI;
import fr.dauphine.repositories.TransactionRepository;
import fr.dauphine.servicesI.ITransactionService;
import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author jihane and zouhair
 */
@Service("transactionService")
@Setter
public class TransactionService implements ITransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private RemoteAccountAPI accountAPI;

    @Autowired
    private ModelMapper mapper;

    @Override
    public OperationDTO externalTransaction(String iban, String ibanDest, Long amount) throws AccountException {
        if (this.accountAPI.checkWhetherAccountExistsOrNo(iban) == Boolean.FALSE) {
            throw new AccountException("the account doesnt exist");
        }
        if (this.accountAPI.checkWhetherAccountExistsOrNo(ibanDest) == Boolean.FALSE) {
            throw new AccountException("the dest account doesnt exist");
        }

        AccountDTO accountDTO = this.accountAPI.findAccountByIban(iban);
        AccountDTO accountDTODest = this.accountAPI.findAccountByIban(ibanDest);


        TransactionEntity tranEntity = TransactionEntity
                .builder()
                .amount(Math.abs(amount))
                .ibanDestination(ibanDest)
                .ibanSource(iban)
                .idTransaction(null)
                .transactionDate(new Timestamp(System.currentTimeMillis()))
                .transactionType(TransactionType.VIREMENT)
                .build();

        tranEntity = this.transactionRepository.save(tranEntity);
        this.accountAPI.updateBalanceByIban(ibanDest, Math.abs(amount));
        this.accountAPI.updateBalanceByIban(iban, -Math.abs(amount));

        return this.mapper.map(tranEntity, OperationDTO.class);
    }

    @Override
    public OperationDTO basicTransaction(String iban, Long amount) throws AccountException {
        if (this.accountAPI.checkWhetherAccountExistsOrNo(iban) == Boolean.FALSE) {
            throw new AccountException("the account doesnt exist");
        }
        
        AccountDTO accountDTO = this.accountAPI.findAccountByIban(iban);

        TransactionEntity tranEntity = TransactionEntity
                .builder()
                .amount(Math.abs(amount))
                .ibanDestination(iban)
                .ibanSource(iban)
                .idTransaction(null)
                .transactionDate(new Timestamp(System.currentTimeMillis()))
                .transactionType(TransactionType.DAB)
                .build();

        tranEntity = this.transactionRepository.save(tranEntity);
        this.accountAPI.updateBalanceByIban(iban, -Math.abs(amount));

        return this.mapper.map(tranEntity, OperationDTO.class);
    }

    @Override
    public Optional<OperationDTO> findTransactionById(Long idTransaction) {
        Optional<TransactionEntity> optTrans = this.transactionRepository.findById(idTransaction);
        System.out.println(this.mapper.map(optTrans.get(), OperationDTO.class) + "<<");;
        return !optTrans.isPresent() ? Optional.empty() : Optional.of(this.mapper.map(optTrans.get(), OperationDTO.class));
    }

    @Override
    public List<OperationDTO> findTransactionsByIban(String iban) throws AccountException {
        if (this.accountAPI.checkWhetherAccountExistsOrNo(iban) == Boolean.FALSE) {
            throw new AccountException("the account doesnt exist");
        }
        
        List<TransactionEntity> transactions = this.transactionRepository.findTransactionsByIBan(iban);
        Type type = new TypeToken<List<OperationDTO>>() {
        }.getType();
        return this.mapper.map(transactions, type);
    }

    @Override
    public List<OperationDTO> searchTransactionsByIbanAndType(String iban, TransactionType transactionType) throws AccountException {
        if (this.accountAPI.checkWhetherAccountExistsOrNo(iban) == Boolean.FALSE) {
            throw new AccountException("the account doesnt exist");
        }
        
        List<TransactionEntity> transactions = this.transactionRepository.findTransactionsByIBanAndType(iban, transactionType);
        Type type = new TypeToken<List<OperationDTO>>() {
        }.getType();
        return this.mapper.map(transactions, type);
    }

    @Override
    public List<OperationDTO> searchTransactionsByIbanBetweenDates(String iban, Timestamp startDate, Timestamp endDate) throws AccountException {
        if (this.accountAPI.checkWhetherAccountExistsOrNo(iban) == Boolean.FALSE) {
            throw new AccountException("the account doesnt exist");
        }
        
        List<TransactionEntity> transactions = this.transactionRepository.findTransactionsByIBanBetweenDates(iban, startDate, endDate);
        Type type = new TypeToken<List<OperationDTO>>() {
        }.getType();
        return this.mapper.map(transactions, type);
    }

    @Override
    public OperationDTO depositMoney(String iban, Long amount) throws AccountException {
        if (this.accountAPI.checkWhetherAccountExistsOrNo(iban) == Boolean.FALSE) {
            throw new AccountException("the account doesnt exist");
        }
        
        TransactionEntity tranEntity = TransactionEntity
                .builder()
                .amount(Math.abs(amount))
                .ibanDestination(iban)
                .ibanSource(iban)
                .idTransaction(null)
                .transactionDate(new Timestamp(System.currentTimeMillis()))
                .transactionType(TransactionType.DEPOSIT)
                .build();
        
        tranEntity = this.transactionRepository.save(tranEntity);
        this.accountAPI.updateBalanceByIban(iban, Math.abs(amount));

        return this.mapper.map(tranEntity, OperationDTO.class);
    }

    
    
}
