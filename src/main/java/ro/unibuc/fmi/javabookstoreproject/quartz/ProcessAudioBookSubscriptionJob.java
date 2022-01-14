package ro.unibuc.fmi.javabookstoreproject.quartz;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import ro.unibuc.fmi.javabookstoreproject.exception.ApiException;
import ro.unibuc.fmi.javabookstoreproject.exception.ExceptionStatus;
import ro.unibuc.fmi.javabookstoreproject.model.Account;
import ro.unibuc.fmi.javabookstoreproject.model.AccountBook;
import ro.unibuc.fmi.javabookstoreproject.model.AudioBook;
import ro.unibuc.fmi.javabookstoreproject.model.Transaction;
import ro.unibuc.fmi.javabookstoreproject.repository.AccountRepository;
import ro.unibuc.fmi.javabookstoreproject.repository.AudioBookRepository;
import ro.unibuc.fmi.javabookstoreproject.repository.TransactionRepository;
import ro.unibuc.fmi.javabookstoreproject.service.AccountBookService;

import javax.transaction.Transactional;
import java.util.List;

@Slf4j
public class ProcessAudioBookSubscriptionJob extends QuartzJobBean {

    private final QuartzService quartzService;
    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;
    private final AccountBookService accountBookService;
    private final AudioBookRepository audioBookRepository;

    @Autowired
    public ProcessAudioBookSubscriptionJob(QuartzService quartzService, TransactionRepository transactionRepository, AccountRepository accountRepository, AccountBookService accountBookService, AudioBookRepository audioBookRepository) {
        this.quartzService = quartzService;
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
        this.accountBookService = accountBookService;
        this.audioBookRepository = audioBookRepository;
    }

    @Override
    @Transactional
    public void executeInternal(@NonNull JobExecutionContext jobExecutionContext) {

        try {
            String jobName = jobExecutionContext.getJobDetail().getKey().getName();
            String schedulerId = jobExecutionContext.getScheduler().getSchedulerInstanceId();
            Long transactionTriggerKey = Long.parseLong(jobExecutionContext.getTrigger().getKey().getName());

            Transaction transaction = transactionRepository.findById(transactionTriggerKey).orElseThrow(
                    () -> new ApiException(ExceptionStatus.TRANSACTION_NOT_FOUND, String.valueOf(transactionTriggerKey)));

            Account account = accountRepository.findById(transaction.getAccount().getId()).orElseThrow(
                    () -> new ApiException(ExceptionStatus.ACCOUNT_NOT_FOUND, String.valueOf(transaction.getAccount().getId())));

            List<AudioBook> audioBookList = audioBookRepository.findAll();

            if (audioBookList.isEmpty()) {
                throw new ApiException(ExceptionStatus.NO_AUDIO_BOOKS_FOUND, "all audioBooks");
            }

            log.warn(audioBookList.toString());

            for (AudioBook audioBook : audioBookList) {

                AccountBook accountBook = AccountBook.builder()
                        .account(account)
                        .audioBook(audioBook)
                        .build();

                accountBookService.createAccountBook(accountBook);

            }

            log.info("Executed job '" + jobName + "' fired from trigger '" + transactionTriggerKey + "' by scheduler with id '" + schedulerId + "'.");

        } catch (ApiException e) {
            log.error(e.getErrorMessage());
            String triggerName = jobExecutionContext.getTrigger().getKey().getName();
            quartzService.deleteAudioBookSubscriptionTrigger(Long.parseLong(triggerName));
        } catch (Exception e) {
            log.error(e.toString());
        }

    }

}
