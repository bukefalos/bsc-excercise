package com.bsc.postalservice.input;

import com.bsc.postalservice.fee.PostalFeeService;
import com.bsc.postalservice.fee.PostalFeeServiceImpl;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;

public class OperationAddFeeTest {

  private OperationAddFee operationAddFee;

  @Before
  public void init() {
    PostalFeeService postalFeeService = new PostalFeeServiceImpl();
    operationAddFee = new OperationAddFee(postalFeeService);
  }

  @Test
  public void canExecute() {
    assertTrue(operationAddFee.canExecute("10 0.50"));
    assertTrue(operationAddFee.canExecute("10.10 0.50"));
    assertTrue(operationAddFee.canExecute("0.123 0.50"));
    assertTrue(operationAddFee.canExecute("0.123 120.00"));
  }

  @Test
  public void cannotExecute() {
    assertFalse(operationAddFee.canExecute(".0 100.10"));
    assertFalse(operationAddFee.canExecute("-4 100.00"));
    assertFalse(operationAddFee.canExecute("0.1000 100.00"));
    assertFalse(operationAddFee.canExecute("10 100"));
    assertFalse(operationAddFee.canExecute("10 100.123"));
  }

  @Test
  public void executionOutput() {
    assertThat(operationAddFee.execute("10 5.10"), equalTo(OperationAddFee.FEE_ADDED));
    assertThat(operationAddFee.execute("0.123 .10"), containsString(OperationAddFee.FEE_WRONG_FORMAT));
  }
}