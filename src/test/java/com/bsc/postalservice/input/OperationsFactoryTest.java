package com.bsc.postalservice.input;

import org.junit.Test;

import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertThat;

public class OperationsFactoryTest {

  @Test
  public void getUnrecognizedOperation() {
    OperationsFactory operationsFactory = new OperationsFactory(new ArrayList<>());
    InputOperation operation = operationsFactory.getOperation("anything");
    assertThat(operation, instanceOf(OperationUnrecognized.class));
  }

}