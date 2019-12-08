package com.bsc.postalservice.schedule;

import com.bsc.postalservice.postalpackage.domain.PostalPackageRepository;
import org.quartz.*;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

public class ConsoleSummaryWriterJob implements Job {

  private static final String JOB_TRIGGER_ID = "summaryTrigger";
  private static final String JOB_ID = "summaryJob";
  private static final String JOB_GROUP = "group1";

  private PostalPackageRepository repository;

  @Override
  public void execute(JobExecutionContext context) throws JobExecutionException {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("\n*** Package summary ***\n");
    stringBuilder.append(repository.getGroupedSummaryByCode());
    stringBuilder.append("=== Package summary end ===");
    System.out.println(stringBuilder.toString());
  }

  public void setRepository(PostalPackageRepository repository) {
    this.repository = repository;
  }

  public static JobDetail createJobDefinition(JobDataMap jobDataMap) {
    return newJob(ConsoleSummaryWriterJob.class)
        .withIdentity(JOB_ID, JOB_GROUP)
        .usingJobData(jobDataMap)
        .build();
  }

  public static Trigger createTriggerDefinition() {
    return  newTrigger()
        .withIdentity(JOB_TRIGGER_ID, JOB_GROUP)
        .startNow()
        .withSchedule(simpleSchedule()
            .withIntervalInSeconds(60)
            .repeatForever())
        .build();
  }
}
