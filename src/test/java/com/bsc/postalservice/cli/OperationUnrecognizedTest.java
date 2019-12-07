package com.bsc.postalservice.cli;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class OperationUnrecognizedTest {

  private CLIOperation operation;

  @Before
  public void init() {
    operation = new OperationUnrecognized();
  }

  @Test
  public void canExecute() {
    assertTrue(operation.canExecute("any"));
  }

  @Test
  public void execute() throws TerminateException {
    assertThat(operation.execute("any"), equalTo(OperationUnrecognized.UNRECOGNIZED_FORMAT_MSG_WITH_HELP));
  }
}