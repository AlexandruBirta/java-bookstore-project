package ro.unibuc.fmi.javabookstoreproject.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.unibuc.fmi.javabookstoreproject.exception.ApiException;
import ro.unibuc.fmi.javabookstoreproject.exception.ExceptionStatus;
import ro.unibuc.fmi.javabookstoreproject.model.Account;
import ro.unibuc.fmi.javabookstoreproject.repository.AccountRepository;

import java.util.List;

@Slf4j
@Service
public class AccountService {

    private final AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Transactional
    public void createAccount(Account account) {

        List<Account> repoAccounts = accountRepository.findAll();

        for (Account repoAccount : repoAccounts) {
            if (repoAccount.getEmail().equals(account.getEmail())) {
                throw new ApiException(ExceptionStatus.ACCOUNT_ALREADY_EXISTS, repoAccount.getEmail());
            }
        }

        accountRepository.save(account);
        log.info("Created " + account);

    }

    public Account getAccountById(Long accountId) {
        return accountRepository.findById(accountId).orElseThrow(
                () -> new ApiException(ExceptionStatus.ACCOUNT_NOT_FOUND, String.valueOf(accountId)));
    }

    public void deleteAccount(Long accountId) {

        if (!accountRepository.existsById(accountId)) {
            throw new ApiException(ExceptionStatus.ACCOUNT_NOT_FOUND, String.valueOf(accountId));
        }

        accountRepository.deleteById(accountId);
        log.info("Deleted account with id '" + accountId + "'");

    }

}
