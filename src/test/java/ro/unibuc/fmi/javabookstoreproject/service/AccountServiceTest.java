package ro.unibuc.fmi.javabookstoreproject.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.test.context.TestPropertySource;
import ro.unibuc.fmi.javabookstoreproject.model.Account;
import ro.unibuc.fmi.javabookstoreproject.repository.AccountRepository;

import java.time.LocalDateTime;

@Slf4j
@ExtendWith(MockitoExtension.class)
@TestPropertySource(locations = "classpath:test.properties")
@EntityScan("ro.unibuc.fmi.javabookstoreproject.model.*")
class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AccountService accountService;

    private static Account testAccount;

    @BeforeAll
    public static void setup() {

        testAccount = Account.builder()
                .id(1L)
                .firstName("Popescu")
                .lastName("Ion")
                .email("ion.pop@email.com")
                .insertedDate(LocalDateTime.now())
                .updatedDate(LocalDateTime.now())
                .build();

    }

    @Test
    @DisplayName("Given test account, when an account is saved, then it can be verified for existence")
    void createAccountTest() {

        Mockito.when(accountRepository.save(Mockito.any(Account.class))).thenReturn(testAccount);
        Mockito.when(accountRepository.getById(testAccount.getId())).thenReturn(testAccount);

        accountService.createAccount(testAccount);
        Account savedAccount = accountRepository.getById(testAccount.getId());


        Assertions.assertEquals(savedAccount, testAccount);

    }

    @Test
    @DisplayName("Given test account, when an account is saved, then it can be deleted")
    void deleteAccountTest() {

        Mockito.when(accountRepository.save(Mockito.any(Account.class))).thenReturn(testAccount);
        Mockito.when(accountRepository.existsById(testAccount.getId())).thenReturn(true);
        Mockito.when(accountRepository.getById(testAccount.getId())).thenReturn(null);

        accountService.createAccount(testAccount);
        accountService.deleteAccount(testAccount.getId());
        Account savedAccount = accountRepository.getById(testAccount.getId());


        Assertions.assertNull(savedAccount);

    }

}
