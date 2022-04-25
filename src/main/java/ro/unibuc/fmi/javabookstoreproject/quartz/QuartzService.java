package ro.unibuc.fmi.javabookstoreproject.quartz;

import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.unibuc.fmi.javabookstoreproject.model.AccountBook;
import ro.unibuc.fmi.javabookstoreproject.model.Transaction;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

@Slf4j
@Service
public class QuartzService {

    private static final JobKey RENTAL_JOB_KEY = new JobKey(ProcessRentalJob.class.getSimpleName(), "rental_transaction_jobs");
    private static final String RENTAL_TRIGGER_GROUP = "rental_transaction_triggers";
    private static final JobKey SUBSCRIPTION_JOB_KEY = new JobKey(ProcessSubscriptionJob.class.getSimpleName(), "subscription_transaction_jobs");
    private static final String SUBSCRIPTION_TRIGGER_GROUP = "subscription_transaction_triggers";
    private static final JobKey AUDIO_BOOK_SUBSCRIPTION_JOB_KEY = new JobKey(ProcessAudioBookSubscriptionJob.class.getSimpleName(), "audio_book_subscription_transaction_jobs");
    private static final String AUDIO_BOOK_SUBSCRIPTION_TRIGGER_GROUP = "audio_book_subscription_transaction_triggers";
    private static final boolean IS_REPLACED = false;

    private final Scheduler scheduler;

    @Autowired
    public QuartzService(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    @PostConstruct
    public void init() {
        try {
            this.scheduler.start();

            if (this.scheduler.getJobDetail(RENTAL_JOB_KEY) == null) {

                JobDetail jobDetail = newJob(ProcessRentalJob.class)
                        .withIdentity(RENTAL_JOB_KEY)
                        .storeDurably()
                        .build();

                this.scheduler.addJob(jobDetail, IS_REPLACED);
            }

            if (this.scheduler.getJobDetail(SUBSCRIPTION_JOB_KEY) == null) {

                JobDetail jobDetail = newJob(ProcessSubscriptionJob.class)
                        .withIdentity(SUBSCRIPTION_JOB_KEY)
                        .storeDurably()
                        .build();

                this.scheduler.addJob(jobDetail, IS_REPLACED);
            }

            if (this.scheduler.getJobDetail(AUDIO_BOOK_SUBSCRIPTION_JOB_KEY) == null) {

                JobDetail jobDetail = newJob(ProcessAudioBookSubscriptionJob.class)
                        .withIdentity(AUDIO_BOOK_SUBSCRIPTION_JOB_KEY)
                        .storeDurably()
                        .build();

                this.scheduler.addJob(jobDetail, IS_REPLACED);
            }

        } catch (SchedulerException e) {
            log.error(String.valueOf(e));
        }
    }

    @PreDestroy
    public void close() {
        try {
            this.scheduler.shutdown();
        } catch (SchedulerException e) {
            log.error(String.valueOf(e));
        }
    }

    public void addRentalTrigger(Transaction transaction, AccountBook accountBook) {

        try {
            String triggerName = String.valueOf(accountBook.getId());
            TriggerKey transactionTriggerKey = new TriggerKey(triggerName, RENTAL_TRIGGER_GROUP);

            if (this.scheduler.getTrigger(transactionTriggerKey) == null) {

                Trigger trigger = newTrigger()
                        .withIdentity(transactionTriggerKey)
                        .startAt(DateBuilder.futureDate(transaction.getRentalTimeInDays(), DateBuilder.IntervalUnit.SECOND))
                        .forJob(RENTAL_JOB_KEY)
                        .build();

                this.scheduler.scheduleJob(trigger);
                log.info("Trigger for rental transaction with id '" + accountBook.getId() + "' added.");
            }

        } catch (SchedulerException e) {
            log.error(String.valueOf(e));
        }

    }

    public void addSubscriptionTrigger(Transaction transaction) {

        try {
            String triggerName = String.valueOf(transaction.getId());
            TriggerKey transactionTriggerKey = new TriggerKey(triggerName, SUBSCRIPTION_TRIGGER_GROUP);

            if (this.scheduler.getTrigger(transactionTriggerKey) == null) {

                Date endDate = convertToDateViaInstant(LocalDateTime.now().plusMonths(transaction.getSubscriptionDurationInMonths()));

                Trigger trigger = newTrigger()
                        .withIdentity(transactionTriggerKey)
                        .forJob(SUBSCRIPTION_JOB_KEY)
                        .withSchedule(cronSchedule("0 * * * * ?"))
                        .endAt(endDate)
                        .startNow()
                        .build();

                this.scheduler.scheduleJob(trigger);
                log.info("Trigger for subscription transaction with id '" + transaction.getId() + "' added.");
            }

        } catch (SchedulerException e) {
            log.error(String.valueOf(e));
        }

    }

    public void addAudioBookSubscriptionTrigger(Transaction transaction) {

        try {
            String triggerName = String.valueOf(transaction.getId());
            TriggerKey transactionTriggerKey = new TriggerKey(triggerName, AUDIO_BOOK_SUBSCRIPTION_TRIGGER_GROUP);

            if (this.scheduler.getTrigger(transactionTriggerKey) == null) {

                Date endDate = convertToDateViaInstant(LocalDateTime.now().plusMonths(transaction.getSubscriptionDurationInMonths()));

                Trigger trigger = newTrigger()
                        .withIdentity(transactionTriggerKey)
                        .forJob(AUDIO_BOOK_SUBSCRIPTION_JOB_KEY)
                        .withSchedule(cronSchedule("0 * * * * ?"))
                        .endAt(endDate)
                        .startNow()
                        .build();

                this.scheduler.scheduleJob(trigger);
                log.info("Trigger for audio book subscription transaction with id '" + transaction.getId() + "' added.");
            }

        } catch (SchedulerException e) {
            log.error(String.valueOf(e));
        }

    }

    public void deleteRentalTrigger(Long transactionId) {

        try {
            String triggerName = transactionId.toString();
            TriggerKey transactionTriggerKey = new TriggerKey(triggerName, RENTAL_TRIGGER_GROUP);

            this.scheduler.unscheduleJob(transactionTriggerKey);
            log.info("Trigger for rental transaction with id '" + transactionId + "' deleted.");

        } catch (SchedulerException e) {
            log.error(String.valueOf(e));
        }

    }

    public void deleteSubscriptionTrigger(Long transactionId) {

        try {
            String triggerName = transactionId.toString();
            TriggerKey transactionTriggerKey = new TriggerKey(triggerName, SUBSCRIPTION_TRIGGER_GROUP);

            this.scheduler.unscheduleJob(transactionTriggerKey);
            log.info("Trigger for subscription transaction with id '" + transactionId + "' deleted.");

        } catch (SchedulerException e) {
            log.error(String.valueOf(e));
        }

    }

    public void deleteAudioBookSubscriptionTrigger(Long transactionId) {

        try {
            String triggerName = transactionId.toString();
            TriggerKey transactionTriggerKey = new TriggerKey(triggerName, AUDIO_BOOK_SUBSCRIPTION_TRIGGER_GROUP);

            this.scheduler.unscheduleJob(transactionTriggerKey);
            log.info("Trigger for audio book subscription transaction with id '" + transactionId + "' deleted.");

        } catch (SchedulerException e) {
            log.error(String.valueOf(e));
        }

    }

    Date convertToDateViaInstant(LocalDateTime dateToConvert) {
        return java.util.Date
                .from(dateToConvert.atZone(ZoneId.systemDefault())
                        .toInstant());
    }

}
