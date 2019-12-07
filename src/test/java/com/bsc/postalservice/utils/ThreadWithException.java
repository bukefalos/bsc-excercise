package com.bsc.postalservice.utils;

public class ThreadWithException extends Thread {

  private Exception exception;

  public ThreadWithException(Runnable target) {
    super(target);
  }

  @Override
  public void run() {
    try {
      super.run();
    } catch(Exception ex) {
      this.exception = ex;
    }
  }

  public Exception joinAndGetException() throws InterruptedException {
    join();
    return exception;
  }
}
