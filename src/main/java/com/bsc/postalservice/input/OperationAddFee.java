package com.bsc.postalservice.input;

import com.bsc.postalservice.fee.PostalFee;
import com.bsc.postalservice.fee.PostalFeeFormatException;
import com.bsc.postalservice.fee.PostalFeeService;


public class OperationAddFee implements InputOperation {

  public static final String FEE_ADDED = "Fee processed";
  public static final String FEE_WRONG_FORMAT = "Fee wrong format";

  private PostalFeeService postalFeeService;

  public OperationAddFee(PostalFeeService postalFeeService) {
    this.postalFeeService = postalFeeService;
  }

  @Override
  public boolean canExecute(String input) {
    return PostalFee.pattern.matcher(input).matches();
  }

  @Override
  public String execute(String input) {
    try {
      PostalFee fee = PostalFee.parseFee(input);
      postalFeeService.addFeeEntry(fee);
    } catch (PostalFeeFormatException pffe) {
      return FEE_WRONG_FORMAT + " " + pffe.getMessage();
    }
    return FEE_ADDED;
  }
}
