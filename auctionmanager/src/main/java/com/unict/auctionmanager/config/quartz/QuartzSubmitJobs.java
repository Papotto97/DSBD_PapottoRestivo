package com.unict.auctionmanager.config.quartz;

import org.quartz.JobDetail;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;

import com.unict.auctionmanager.job.CustomQuartzJob;

public class QuartzSubmitJobs {
	
	private static final String CRON_EVERY_FIVE_MINUTES = "0 */1 * ? * *";

    @Bean(name = "memberClassStats")
    public JobDetailFactoryBean jobMemberClassStats() {
        return QuartzConfig.createJobDetail(CustomQuartzJob.class, "Class Statistics Job");
    }

    @Bean(name = "memberClassStatsTrigger")
    public CronTriggerFactoryBean triggerMemberClassStats(@Qualifier("memberClassStats") JobDetail jobDetail) {
        //return QuartzConfig.createCronTrigger(jobDetail, "Class Statistics Trigger");
        return QuartzConfig.createCronTrigger(jobDetail, CRON_EVERY_FIVE_MINUTES, "Class Statistics Trigger");
    }

}
