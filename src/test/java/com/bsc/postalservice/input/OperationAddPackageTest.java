package com.bsc.postalservice.input;

import com.bsc.postalservice.fee.PostalFeeService;
import com.bsc.postalservice.postalpackage.InMemoryPostalPackageRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class OperationAddPackageTest {

  private OperationAddPackage operationAddPackage;

  @Mock
  private PostalFeeService mockFeeService;

  @Before
  public void init() {
    MockitoAnnotations.initMocks(this);
    operationAddPackage = new OperationAddPackage(new InMemoryPostalPackageRepository());
  }

  @Test
  public void canExecute() {
    assertTrue(operationAddPackage.canExecute("123.123 12345"));
    assertTrue(operationAddPackage.canExecute("123 12345"));
    assertTrue(operationAddPackage.canExecute("1.0 12345"));
    assertTrue(operationAddPackage.canExecute("1 12345"));
  }

  @Test
  public void cannotExecute() {
    assertFalse(operationAddPackage.canExecute("123. 12345"));
    assertFalse(operationAddPackage.canExecute(".123 12345"));
    assertFalse(operationAddPackage.canExecute("123.123 1234"));
    assertFalse(operationAddPackage.canExecute("12.12"));
    assertFalse(operationAddPackage.canExecute("-20 12345"));
  }

  @Test
  public void executionOutput() {
    assertThat(operationAddPackage.execute("123.12 12345"), equalTo(OperationAddPackage.PACKAGE_ADDED));
    assertThat(operationAddPackage.execute(".123 12345"), containsString(OperationAddPackage.PACKAGE_WRONG_FORMAT));
  }

  @Test
  public void operationWorksWithFeeService() {
    OperationAddPackage operationAddPackage = new OperationAddPackage(new InMemoryPostalPackageRepository(), mockFeeService);
    operationAddPackage.execute("12 12345");

    verify(mockFeeService, times(1)).getFeeBasedOnWeight(12f);
  }
}