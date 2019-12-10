package com.bsc.postalservice.input;

import java.util.List;

public class OperationsFactory {

  private List<InputOperation> operations;

  public OperationsFactory(List<InputOperation> operations) {
    this.operations = operations;
  }

  public InputOperation getOperation(String input) {
    return operations
        .stream()
        .filter(op -> op.canExecute(input))
        .findFirst()
        .orElse(new OperationUnrecognized());
  }
}
