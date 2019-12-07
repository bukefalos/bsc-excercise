package com.bsc.postalservice.cli;

public class OperationUnrecognized implements CLIOperation {

  public static String UNRECOGNIZED_FORMAT_MSG_WITH_HELP = "Unrecognized input format, please try again";


  @Override
  public boolean canExecute(String input) {
    return true;
  }

  @Override
  public String execute(String input) {
     return UNRECOGNIZED_FORMAT_MSG_WITH_HELP;
  }
}
