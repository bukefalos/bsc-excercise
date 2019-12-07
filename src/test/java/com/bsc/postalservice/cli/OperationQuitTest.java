package com.bsc.postalservice.cli;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class OperationQuitTest {

  private CLIOperation operation;

  @Before
  public void init() {
    operation = new OperationQuit();
  }

  @Test
  public void canExecute() {
    assertTrue(operation.canExecute("quit"));
  }

  @Test(expected = TerminateException.class)
  public void execute() throws TerminateException {
    operation.execute("any");

    //should not get there
    fail();
  }
}