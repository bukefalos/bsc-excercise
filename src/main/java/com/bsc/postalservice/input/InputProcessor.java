package com.bsc.postalservice.input;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

/**
 * This processor handles InputStream by reading it by lines.
 * For each line it assigns {@link InputOperation} by requesting {@link OperationsFactory}.
 * Each {@link InputOperation} is executed and result is appended to {@link PrintStream}
 */
public class InputProcessor {

  private OperationsFactory operationsFactory;

  public InputProcessor(OperationsFactory operationsFactory) {
    this.operationsFactory = operationsFactory;
  }

  /**
   * Reads InputStream and forwards result of assigned operation for each line to PrintStream.
   * It closes InputStream after reading whole stream or in case {@link TerminateException} occurs
   * @param input
   * @param out
   * @throws TerminateException signaling that processor has read terminal operation and will discontinue
   * reading provided input.
   */
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
