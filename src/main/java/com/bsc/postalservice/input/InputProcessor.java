package com.bsc.postalservice.input;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class InputProcessor {

  private OperationsFactory operationsFactory;

  public InputProcessor(OperationsFactory operationsFactory) {
    this.operationsFactory = operationsFactory;
  }

  public void processInput(InputStream input, PrintStream out) throws TerminateException {
    try(Scanner inputScanner = new Scanner(input)) {
      do {
        String line = inputScanner.nextLine();
        InputOperation operation = operationsFactory.getOperation(line);
        out.println(operation.execute(line));

      } while (inputScanner.hasNext());
    }
  }

}
