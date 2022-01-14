package ro.unibuc.fmi.javabookstoreproject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import ro.unibuc.fmi.javabookstoreproject.api.AccountApi;
import ro.unibuc.fmi.javabookstoreproject.model.Account;
import ro.unibuc.fmi.javabookstoreproject.service.AccountService;

@RestController
public class AccountController implements AccountApi {

    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public void createAccount(Account account) {
        accountService.createAccount(account);
    }

    @Override
    public Account getAccountById(Long accountId) {
        return accountService.getAccountById(accountId);
    }

    @Override
    public void deleteAccount(Long accountId) {
        accountService.deleteAccount(accountId);
    }

}
