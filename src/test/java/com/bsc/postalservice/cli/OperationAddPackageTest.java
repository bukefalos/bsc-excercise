package com.bsc.postalservice.cli;

import com.bsc.postalservice.postalpackage.infrastructure.InMemoryPostalPackageRepository;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;

public class OperationAddPackageTest {

  private OperationAddPackage operationAdd;

  @Before
  public void init() {
    operationAdd = new OperationAddPackage(new InMemoryPostalPackageRepository());
  }

  @Test
  public void canExecute() {
    assertTrue(operationAdd.canExecute("123.123 12345"));
    assertTrue(operationAdd.canExecute("123 12345"));
    assertTrue(operationAdd.canExecute("1.0 12345"));
    assertTrue(operationAdd.canExecute("1 12345"));
  }

  @Test
  public void cannotExecute() {
    assertFalse(operationAdd.canExecute("123. 12345"));
    assertFalse(operationAdd.canExecute(".123 12345"));
    assertFalse(operationAdd.canExecute("123.123 1234"));
    assertFalse(operationAdd.canExecute("12.12"));
  }

  @Test
  public void executionOutput() {
    assertThat(operationAdd.execute("123.12 12345"), equalTo(OperationAddPackage.PACKAGE_ADDED));
    assertThat(operationAdd.execute(".123 12345"), equalTo(OperationAddPackage.PACKAGE_WRONG_FORMAT));
  }
}