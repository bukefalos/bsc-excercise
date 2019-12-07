package com.bsc.postalservice.cli;

public interface CLIOperation {

  boolean canExecute(String input);

  String execute(String input) throws TerminateException;
}
