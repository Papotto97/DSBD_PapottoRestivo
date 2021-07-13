package com.unict.auctionmanager.config.quartz;

import java.io.StringReader;
import java.util.Calendar;
import java.util.Properties;

import javax.sql.DataSource;

import org.quartz.CronTrigger;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobDetail;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;

import com.unict.auctionmanager.job.CustomQuartzJob;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;


@Configuration
@PropertySource("classpath:quartz.properties")
@DisallowConcurrentExecution
public class QuartzConfig 
{
    @Autowired
	private ApplicationContext applicationContext;

    @Value("#{T(com.unict.auctionmanager.config.quartz.ResourceReader).readFileToString('classpath:quartz.properties')}")
    private String resource;

    @Value("${org.quartz.uri}")
    private String url;

    @Value("${org.quartz.username}")
    private String username;

    @Value("${org.quartz.password}")
    private String password;

    @Value("${org.quartz.cron.default}")
    private static String defaultCron;
    
	private static final String CRON_EVERY_FIVE_MINUTES = "0 */1 * ? * *";


    private DataSource dataSourceQRTZ() {
        HikariConfig cpConfig = new HikariConfig();
        cpConfig.setJdbcUrl(url);
        cpConfig.setUsername(username);
        cpConfig.setPassword(password);
        cpConfig.setMaximumPoolSize(15);
        cpConfig.setConnectionTestQuery("SELECT 1");

        // performance senstive settings
        cpConfig.setMinimumIdle(0);
        cpConfig.setConnectionTimeout(30000);
        cpConfig.setIdleTimeout(30000);
        cpConfig.setMaxLifetime(30000);

        cpConfig.setSchema("qrtz");

        cpConfig.setDriverClassName("org.postgresql.Driver");

        HikariDataSource cpDatasource = new HikariDataSource(cpConfig);
        return cpDatasource;
    }

    /*public QuartzConfig(ApplicationContext applicationContext, DataSource dataSource) {
        this.applicationContext = applicationContext;
        this.dataSource = dataSource;
    }*/

    @Bean
    public SpringBeanJobFactory springBeanJobFactory() {
        AutowiringSpringBeanJobFactory jobFactory = new AutowiringSpringBeanJobFactory();
        jobFactory.setApplicationContext(applicationContext);
        
        return jobFactory;
    }

    @Bean
    public SchedulerFactoryBean scheduler(Trigger... triggers) {
        SchedulerFactoryBean schedulerFactory = new SchedulerFactoryBean();
        //String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        //String appConfigPath = rootPath + "quartz.properties";
        Properties properties = new Properties();
        try {
        	properties.load(new StringReader(resource));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        properties.setProperty("org.quartz.scheduler.instanceName", "SellCommodities");
        properties.setProperty("org.quartz.scheduler.instanceId", "SellCommoditiesInstance");

        schedulerFactory.setOverwriteExistingJobs(true);
        schedulerFactory.setAutoStartup(true);
        schedulerFactory.setQuartzProperties(properties);
        schedulerFactory.setDataSource(dataSourceQRTZ());
        schedulerFactory.setJobFactory(springBeanJobFactory());
        schedulerFactory.setWaitForJobsToCompleteOnShutdown(true);
        

        if (triggers!=null) {
            schedulerFactory.setTriggers(triggers);
        }

        return schedulerFactory;
    }
    
    
  @Bean(name = "memberClassStats")
  public JobDetailFactoryBean jobMemberClassStats() {
      return QuartzConfig.createJobDetail(CustomQuartzJob.class, "ClassStatisticsJob");
  }
    
    static SimpleTriggerFactoryBean createTrigger(JobDetail jobDetail, long pollFrequencyMs, String triggerName) {

        SimpleTriggerFactoryBean factoryBean = new SimpleTriggerFactoryBean();
        factoryBean.setJobDetail(jobDetail);
        factoryBean.setStartDelay(0L);
        factoryBean.setRepeatInterval(pollFrequencyMs);
        factoryBean.setName(triggerName);
        factoryBean.setRepeatCount(SimpleTrigger.REPEAT_INDEFINITELY);
        factoryBean.setMisfireInstruction(SimpleTrigger.MISFIRE_INSTRUCTION_RESCHEDULE_NEXT_WITH_REMAINING_COUNT);

        return factoryBean;
    }
    
    
    @Bean(name = "memberClassStatsTrigger")
    public CronTriggerFactoryBean triggerMemberClassStats(@Qualifier("memberClassStats") JobDetail jobDetail) {
        //return QuartzConfig.createCronTrigger(jobDetail, "Class Statistics Trigger");
        return QuartzConfig.createCronTrigger(jobDetail, CRON_EVERY_FIVE_MINUTES, "ClassStatisticsTrigger");
    }
    
    static CronTriggerFactoryBean createCronTrigger(JobDetail jobDetail, String cronExpression, String triggerName) {
    //static CronTriggerFactoryBean createCronTrigger(JobDetail jobDetail, String triggerName) {

        // To fix an issue with time-based cron jobs
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        CronTriggerFactoryBean factoryBean = new CronTriggerFactoryBean();
        factoryBean.setJobDetail(jobDetail);
        factoryBean.setCronExpression(cronExpression); //TODO check sul db la cron expression valida
//        factoryBean.setCronExpression(cronExpression); //TODO check sul db la cron expression valida
        factoryBean.setStartTime(calendar.getTime());
        factoryBean.setStartDelay(0L);
        factoryBean.setName(triggerName);
        factoryBean.setMisfireInstruction(CronTrigger.MISFIRE_INSTRUCTION_DO_NOTHING);

        return factoryBean;
    }

    static JobDetailFactoryBean createJobDetail(Class jobClass, String jobName) {
        JobDetailFactoryBean factoryBean = new JobDetailFactoryBean();
        factoryBean.setName(jobName);
        factoryBean.setJobClass(jobClass);
        factoryBean.setDurability(true);

        return factoryBean;
    }
}
