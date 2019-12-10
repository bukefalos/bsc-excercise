package com.bsc.postalservice.schedule;

/**
 * Job is abstraction for functionality to be scheduled and executed later at fixed period repetitions.
 * Job can be scheduled with {@link FixedPeriodJobScheduler}
 */
public interface Job {

  /**
   * Main body of functionality that needs to be scheduled
   * @throws JobExecutionException whenever job fails to execute its logic
   */
  void execute() throws JobExecutionException;
}
