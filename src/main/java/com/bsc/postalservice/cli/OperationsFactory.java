package com.bsc.postalservice.cli;

import java.util.List;

public class OperationsFactory {

  private List<CLIOperation> operations;

  public OperationsFactory(List<CLIOperation> operations) {
    this.operations = operations;
  }

  public CLIOperation getOperation(String input) {
    return operations
        .stream()
        .filter(op -> op.canExecute(input))
        .findFirst()
        .orElse(new OperationUnrecognized());
  }
}
