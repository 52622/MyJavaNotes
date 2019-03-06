package com.cp;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

/**
 * @author copywang
 * @description
 * @create 2018-10-14 15:30
 */
public class QuartzTest {
    public static void main(String[] args) {

        try {
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
            scheduler.start();

            JobDetail job = JobBuilder.newJob(HelloJob.class)
                    .withIdentity("job1","jobGroup1")
                    .usingJobData("stringValue","string")
                    .usingJobData("floatValue",3.14f)
                    .build();

            Trigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity("trigger1","triggerGroup1")
                    .usingJobData("triggerStringValue","triggerString")
                    .usingJobData("stringValue","string --- change")
                    //.startNow()
                    .startAt(DateBuilder.futureDate(5, DateBuilder.IntervalUnit.MINUTE))
                    .withSchedule(SimpleScheduleBuilder
                            .simpleSchedule()
                            .withIntervalInSeconds(10)
                            .repeatForever().withMisfireHandlingInstructionNowWithExistingCount())
                    .build();
            scheduler.scheduleJob(job,trigger);
            //scheduler.shutdown();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }
}
