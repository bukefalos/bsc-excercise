package com.bsc.postalservice.schedule;

public interface Job {

  void execute() throws JobExecutionException;
}
