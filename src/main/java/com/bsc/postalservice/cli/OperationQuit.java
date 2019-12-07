package com.bsc.postalservice.cli;

import java.util.regex.Pattern;

import static java.util.regex.Pattern.compile;

public class OperationQuit implements CLIOperation {

  private Pattern pattern = compile("^quit$");

  @Override
  public boolean canExecute(String input) {
    return pattern.matcher(input).matches();
  }

  @Override
  public String execute(String input) throws TerminateException {
    throw new TerminateException();
  }
}
