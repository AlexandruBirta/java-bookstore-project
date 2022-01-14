package ro.unibuc.fmi.javabookstoreproject.quartz;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import ro.unibuc.fmi.javabookstoreproject.exception.ApiException;
import ro.unibuc.fmi.javabookstoreproject.exception.ExceptionStatus;
import ro.unibuc.fmi.javabookstoreproject.model.AccountBook;
import ro.unibuc.fmi.javabookstoreproject.repository.AccountBookRepository;

import javax.transaction.Transactional;

@Slf4j
public class ProcessRentalJob extends QuartzJobBean {

    private final QuartzService quartzService;
    private final AccountBookRepository accountBookRepository;

    @Autowired
    public ProcessRentalJob(QuartzService quartzService, AccountBookRepository accountBookRepository) {
        this.quartzService = quartzService;
        this.accountBookRepository = accountBookRepository;
    }

    @Override
    @Transactional
    public void executeInternal(@NonNull JobExecutionContext jobExecutionContext) {

        try {
            String jobName = jobExecutionContext.getJobDetail().getKey().getName();
            String schedulerId = jobExecutionContext.getScheduler().getSchedulerInstanceId();
            Long accountBookTriggerKey = Long.parseLong(jobExecutionContext.getTrigger().getKey().getName());

            AccountBook accountBook = accountBookRepository.findById(accountBookTriggerKey).orElseThrow(
                    () -> new ApiException(ExceptionStatus.ACCOUNT_BOOK_NOT_FOUND, String.valueOf(accountBookTriggerKey)));

            accountBookRepository.delete(accountBook);

            log.info("Executed job '" + jobName + "' fired from trigger '" + accountBookTriggerKey + "' by scheduler with id '" + schedulerId + "'.");

        } catch (ApiException e) {
            log.error(e.getErrorMessage());
            String triggerName = jobExecutionContext.getTrigger().getKey().getName();
            quartzService.deleteRentalTrigger(Long.parseLong(triggerName));
        } catch (Exception e) {
            log.error(e.toString());
        }

    }

}