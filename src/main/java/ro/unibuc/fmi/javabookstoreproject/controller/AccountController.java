package ro.unibuc.fmi.javabookstoreproject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import ro.unibuc.fmi.javabookstoreproject.api.AccountApi;
import ro.unibuc.fmi.javabookstoreproject.model.Account;
import ro.unibuc.fmi.javabookstoreproject.service.AccountService;

import javax.validation.Valid;

@Controller
public class AccountController implements AccountApi {

    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public String createAccount(@ModelAttribute @Valid Account account) {
        accountService.createAccount(account);
        return "create_account.html";
    }

    @Override
    public String createNewAccount(Model model) {
        model.addAttribute("account", new Account());
        return "create_account.html";
    }

    @Override
    public String getAccountById(Long accountId, Model model) {
        model.addAttribute("account", accountService.getAccountById(accountId));
        return "get_account_by_id.html";
    }

    @Override
    public void deleteAccount(Long accountId) {
        accountService.deleteAccount(accountId);
    }

    @Override
    public void updateEmail(Long accountId, String email) {
        accountService.updateEmail(accountId, email);
    }

}