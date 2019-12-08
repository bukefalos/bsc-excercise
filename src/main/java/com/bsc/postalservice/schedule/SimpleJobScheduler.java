package com.bsc.postalservice.schedule;

import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;

public class SimpleJobScheduler {

  private Scheduler scheduler;

  public void scheduleJob(JobDetail jobDetail, Trigger trigger) throws SchedulerException {
    if(scheduler == null) {
      scheduler = StdSchedulerFactory.getDefaultScheduler();
      scheduler.start();
    }
    scheduler.scheduleJob(jobDetail, trigger);
  }

  public void shutDown() {
    try {
      if(scheduler != null) {
        scheduler.shutdown();
      }
    } catch (SchedulerException se) {
      System.out.println("Unable to shutdown scheduler");
    }
  }
}
