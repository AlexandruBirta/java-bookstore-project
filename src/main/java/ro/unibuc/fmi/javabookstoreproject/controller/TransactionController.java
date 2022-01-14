package ro.unibuc.fmi.javabookstoreproject.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import ro.unibuc.fmi.javabookstoreproject.api.TransactionApi;
import ro.unibuc.fmi.javabookstoreproject.model.AccountBook;
import ro.unibuc.fmi.javabookstoreproject.model.Transaction;
import ro.unibuc.fmi.javabookstoreproject.quartz.QuartzService;
import ro.unibuc.fmi.javabookstoreproject.service.AccountBookService;
import ro.unibuc.fmi.javabookstoreproject.service.TransactionService;

@Slf4j
@RestController
public class TransactionController implements TransactionApi {

    private final TransactionService transactionService;
    private final AccountBookService accountBookService;
    private final QuartzService quartzService;

    @Autowired
    public TransactionController(TransactionService transactionService, AccountBookService accountBookService, QuartzService quartzService) {
        this.transactionService = transactionService;
        this.accountBookService = accountBookService;
        this.quartzService = quartzService;
    }

    @Override
    public void makeTransaction(Transaction transaction) {
        Transaction savedTransaction = transactionService.makeTransaction(transaction);
        log.info("Created " + savedTransaction);


        switch (savedTransaction.getType()) {
            case BOOK_PURCHASE:
                AccountBook purchaseAccountBook = AccountBook.builder()
                        .account(savedTransaction.getAccount())
                        .book(savedTransaction.getBook())
                        .rentalDurationInDays(transaction.getRentalTime())
                        .subscriptionDurationInMonths(transaction.getSubscriptionDurationInMonths())
                        .build();
                accountBookService.createAccountBook(purchaseAccountBook);
                break;

            case BOOK_RENTAL:
                AccountBook rentalAccountBook = AccountBook.builder()
                        .account(savedTransaction.getAccount())
                        .book(savedTransaction.getBook())
                        .rentalDurationInDays(transaction.getRentalTime())
                        .subscriptionDurationInMonths(transaction.getSubscriptionDurationInMonths())
                        .build();
                accountBookService.createAccountBook(rentalAccountBook);
                quartzService.addRentalTrigger(transaction, rentalAccountBook);
                break;

            case BOOK_SUBSCRIPTION:
                quartzService.addSubscriptionTrigger(transaction);
                break;

            case AUDIO_BOOK_SUBSCRIPTION:
                quartzService.addAudioBookSubscriptionTrigger(transaction);
                break;
        }

    }

}
