package com.bsc.postalservice.input;

public interface InputOperation {

  boolean canExecute(String input);

  String execute(String input) throws TerminateException;
}
