package ro.unibuc.fmi.javabookstoreproject.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.unibuc.fmi.javabookstoreproject.exception.ApiException;
import ro.unibuc.fmi.javabookstoreproject.exception.ExceptionStatus;
import ro.unibuc.fmi.javabookstoreproject.model.Transaction;
import ro.unibuc.fmi.javabookstoreproject.repository.AccountRepository;
import ro.unibuc.fmi.javabookstoreproject.repository.BookRepository;
import ro.unibuc.fmi.javabookstoreproject.repository.TransactionRepository;

@Slf4j
@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;
    private final BookRepository bookRepository;

    @Autowired
    public TransactionService(TransactionRepository transactionRepository, AccountRepository accountRepository, BookRepository bookRepository) {
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
        this.bookRepository = bookRepository;
    }

    @Transactional
    public Transaction makeTransaction(Transaction transaction) {

        if (!accountRepository.existsById(transaction.getAccount().getId())) {
            throw new ApiException(ExceptionStatus.ACCOUNT_NOT_FOUND, String.valueOf(transaction.getAccount().getId()));
        }

        if (transaction.getBook() != null) {
            if (!bookRepository.existsById(transaction.getBook().getId())) {
                throw new ApiException(ExceptionStatus.BOOK_NOT_FOUND, String.valueOf(transaction.getBook().getId()));
            }
        }

        return transactionRepository.save(transaction);

    }

}
