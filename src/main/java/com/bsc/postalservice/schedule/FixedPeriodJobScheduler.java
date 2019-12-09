package com.bsc.postalservice.schedule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class FixedPeriodJobScheduler {

  private static final Logger LOG = LoggerFactory.getLogger(FixedPeriodJobScheduler.class);

  private ScheduledExecutorService scheduler;

  public void scheduleJob(Job job, FixedPeriodJobDetail jobDetail) {
    if (scheduler == null) {
      scheduler = Executors.newScheduledThreadPool(1);
    }
    scheduler.scheduleAtFixedRate(
        wrapToRunnable(job),
        jobDetail.getDelay(),
        jobDetail.getPeriod(),
        jobDetail.getTimeUnit());
  }

  private Runnable wrapToRunnable(Job job) {
    return () -> {
      try {
        job.execute();
      } catch (JobExecutionException sje) {
        LOG.error("Oops something went wrong during job execution:" + sje.getMessage());
      }
    };
  }

  public void shutDown() {
    try {
      if (scheduler != null) {
        scheduler.shutdown();
      }
    } catch (SecurityException ex) {
      LOG.error("Unable to shutdown scheduler");
    }
  }
}
