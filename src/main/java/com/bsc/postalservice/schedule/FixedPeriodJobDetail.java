package com.bsc.postalservice.schedule;

import lombok.Value;

import java.util.concurrent.TimeUnit;

@Value
public class FixedPeriodJobDetail {
  Integer delay;
  Integer period;
  TimeUnit timeUnit;
}
