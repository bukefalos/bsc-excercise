package com.bsc.postalservice.input;

/**
 * Input operation represents input command parsed from string.
 * Input operations are loaded into {@link OperationsFactory} which will pickup first
 * operation that returns true from canExecute method.
 */
public interface InputOperation {

  /**
   * @param input to operation to find out if it is capable to process it
   * @return true if operation can process this kind of input format
   */
  boolean canExecute(String input);

  /**
   *
   * @param input for operation to process. It is highly recommended to call canExecute method before
   * @return any result of operation in form of String
   * @throws TerminateException to signal that operation was terminal operation and
   * program should stop sending/processing input
   */
  String execute(String input) throws TerminateException;
}
