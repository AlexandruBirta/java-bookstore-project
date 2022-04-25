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
import ro.unibuc.fmi.javabookstoreproject.model.*;
import ro.unibuc.fmi.javabookstoreproject.repository.AccountBookRepository;
import ro.unibuc.fmi.javabookstoreproject.repository.AccountRepository;
import ro.unibuc.fmi.javabookstoreproject.repository.BookRepository;

import java.time.LocalDateTime;

@Slf4j
@ExtendWith(MockitoExtension.class)
@TestPropertySource(locations = "classpath:test.properties")
@EntityScan("ro.unibuc.fmi.javabookstoreproject.model.*")
class AccountBookServiceTest {

    @Mock
    private AccountBookRepository accountBookRepository;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private AccountBookService accountBookService;

    private static Account testAccount;
    private static Author testAuthor;
    private static Publisher testPublisher;
    private static Book testBook;
    private static AccountBook testAccountBook;

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

        testAuthor = Author.builder()
                .id(1L)
                .firstName("Harper")
                .lastName("Lee")
                .insertedDate(LocalDateTime.now())
                .updatedDate(LocalDateTime.now())
                .build();

        testPublisher = Publisher.builder()
                .id(1L)
                .name("Penguin Books")
                .description("Penguin Books was originally a British publishing house. It was co-founded in 1935 by Sir Allen Lane with his brothers Richard and John, as a line of the publishers The Bodley Head, only becoming a separate company the following year.")
                .insertedDate(LocalDateTime.now())
                .updatedDate(LocalDateTime.now())
                .build();


        testBook = Book.builder()
                .id(1L)
                .name("To Kill a Mockingbird")
                .description("To Kill a Mockingbird has become a classic of modern American literature, winning the Pulitzer Prize. The plot and characters are loosely based on Lee's observations of her family, her neighbors and an event that occurred near her hometown of Monroeville, Alabama, in 1936, when she was ten.")
                .genre(Genre.NOVEL)
                .isbn("063122581-1")
                .author(testAuthor)
                .publisher(testPublisher)
                .insertedDate(LocalDateTime.now())
                .updatedDate(LocalDateTime.now())
                .build();

        testAccountBook = AccountBook.builder()
                .account(testAccount)
                .book(testBook)
                .build();

    }

    @Test
    @DisplayName("Given test account book, when a account book is saved, then it can be verified for existence")
    void makeAccountBookTest() {

        Mockito.when(accountBookRepository.getById(testAccountBook.getId())).thenReturn(testAccountBook);
        Mockito.when(accountBookRepository.save(Mockito.any(AccountBook.class))).thenReturn(testAccountBook);

        accountBookService.createAccountBook(testAccountBook);
        AccountBook savedAccountBook = accountBookRepository.getById(testAccountBook.getId());
        log.info(savedAccountBook.toString());

        Assertions.assertEquals(savedAccountBook, testAccountBook);

    }

}