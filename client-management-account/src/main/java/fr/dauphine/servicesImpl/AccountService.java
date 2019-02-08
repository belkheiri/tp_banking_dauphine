/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.dauphine.servicesImpl;

import fr.dauphine.accounts.dtos.AccountDTO;
import fr.dauphine.accounts.dtos.AccountDetailDTO;
import fr.dauphine.accounts.dtos.OperationDTO;
import fr.dauphine.accounts.enums.AccountType;
import fr.dauphine.entities.AccountEntity;
import fr.dauphine.exceptions.AccountException;
import fr.dauphine.remotes.RemoteTransactionAPI;
import fr.dauphine.repositories.AccountRepository;
import fr.dauphine.servicesI.IAccountService;
import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import org.iban4j.CountryCode;
import org.iban4j.Iban;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author jihane and zouhair
 */
@Service("accountService")
public class AccountService implements IAccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private RemoteTransactionAPI transactionAPI;

    @Value("${bank.code}")
    private String bankCode;

    @Override
    @Transactional
    public boolean checkAccountExists(String iban) {
        return this.accountRepository.existsById(iban);
    }

    @Override
    @Transactional
    public AccountDTO findAccountByIban(String iban) throws AccountException {
        Optional<AccountEntity> optAccount = this.accountRepository.findById(iban);
        if (!optAccount.isPresent()) {
            throw new AccountException("the account does not exist");
        }
        return this.mapper.map(optAccount.get(), AccountDTO.class);
    }

    @Override
    @Transactional
    public AccountDTO createAccount(CountryCode countryCode, AccountType accountType, Long accountFee, Long interest) {
        Iban iban = generateIban(countryCode);
        while (checkAccountExists(iban.toFormattedString())) {
            iban = generateIban(countryCode);
        }

        AccountEntity accountEntity = AccountEntity.builder()
                .balance(0L)
                .interest(interest)
                .accountFee(accountFee)
                .creationDate(new Timestamp(System.currentTimeMillis()))
                .accountType(accountType)
                .iban(iban.toFormattedString())
                .build();
        this.accountRepository.save(accountEntity);
        return this.mapper.map(accountEntity, AccountDTO.class);
    }

    @Override
    @Transactional
    public AccountDTO updateAccount(String iban, Long accoutFee, Long interest) throws AccountException {
        Optional<AccountEntity> optAccount = this.accountRepository.findById(iban);
        if (!optAccount.isPresent()) {
            throw new AccountException("the account does not exist");
        }
        AccountEntity accountEntity = optAccount.get();
        accountEntity.setInterest(interest);
        accountEntity.setAccountFee(accoutFee);
        accountEntity = this.accountRepository.save(accountEntity);
        return this.mapper.map(accountEntity, AccountDTO.class);
    }

    @Override
    @Transactional
    public AccountDetailDTO getAccountDetail(String iban) throws AccountException{
        AccountDTO accountDTO = this.findAccountByIban(iban);
        ResponseEntity<List<OperationDTO>> responseEntity = this.transactionAPI.findAllTransactionsByIban(iban);
        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            return AccountDetailDTO
                    .builder()
                    .account(accountDTO)
                    .operations((List<OperationDTO>) responseEntity.getBody())
                    .build();
        } else {
            throw new AccountException("cannot fetch for account transactions");
        }
    }

    @Override
    @Transactional
    public List<AccountDTO> findAll() {
        Type type = new TypeToken<List<AccountDTO>>() {
        }.getType();
        List<AccountEntity> accountEntities = this.accountRepository.findAll();
        return this.mapper.map(accountEntities, type);
    }

    @Override
    @Transactional
    public AccountDTO updateAccountBalanceByIban(String iban, Long amount) throws AccountException {
        if (!this.checkAccountExists(iban)) {
            throw new AccountException("the account dest does not exist");
        }
        this.accountRepository.addAmountByIban(iban, amount);
        return this.findAccountByIban(iban);
    }

    private Iban generateIban(CountryCode countryCode) {
        return new Iban.Builder()
                .countryCode(countryCode)
                .bankCode(bankCode)
                .buildRandom();
    }

}
