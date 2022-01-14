package ro.unibuc.fmi.javabookstoreproject.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.unibuc.fmi.javabookstoreproject.exception.ApiException;
import ro.unibuc.fmi.javabookstoreproject.exception.ExceptionStatus;
import ro.unibuc.fmi.javabookstoreproject.model.AccountBook;
import ro.unibuc.fmi.javabookstoreproject.repository.AccountBookRepository;

import java.util.List;

@Slf4j
@Service
public class AccountBookService {

    private final AccountBookRepository accountBookRepository;

    @Autowired
    public AccountBookService(AccountBookRepository accountBookRepository) {
        this.accountBookRepository = accountBookRepository;
    }

    public void createAccountBook(AccountBook accountBook) {

        List<AccountBook> repoAccountBooks = accountBookRepository.findAll();

        for (AccountBook repoAccountBook : repoAccountBooks) {
            if (accountBook.getAccount().equals(repoAccountBook.getAccount().getId())
                    && accountBook.getBook().equals(repoAccountBook.getBook().getId())) {
                throw new ApiException(ExceptionStatus.ACCOUNT_BOOK_ALREADY_EXISTS, repoAccountBook.getAccount().getId() + " " + repoAccountBook.getBook().getIsbn());
            }
        }

        accountBookRepository.save(accountBook);
        log.info("Created " + accountBook);

    }

}
