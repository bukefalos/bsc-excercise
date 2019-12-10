package com.bsc.postalservice.schedule;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class FixedPeriodJobSchedulerTest {

  private FixedPeriodJobScheduler jobScheduler;

  @Mock
  private ScheduledExecutorService executorService;

  @Before
  public void init() {
    MockitoAnnotations.initMocks(this);
    jobScheduler = new FixedPeriodJobScheduler(executorService);
  }

  @Test
  public void scheduleJob() {
    Integer delay = 5;
    Integer period = 5;
    TimeUnit timeUnit = SECONDS;
    jobScheduler.scheduleJob(emptyJob(), new FixedPeriodJobDetail(delay, period, timeUnit));

    verify(executorService, times(1))
        .scheduleAtFixedRate(any(Runnable.class), eq((long) delay), eq((long) period), eq(timeUnit));
  }

  @Test
  public void shutDown() {
    jobScheduler.shutDown();

    verify(executorService, times(1)).shutdown();
  }

  private Job emptyJob() {
    return () -> { };
  }
}