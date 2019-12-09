package com.bsc.postalservice.fee;

import lombok.Value;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Value
public class PostalFee {

  private static String decimal2FixedDigits = "(\\d+\\.\\d{2})";
  private static String digitMax3Decimal = "(\\d+(?:\\.\\d{1,3})?)";
  private static String inputPattern = "^" + digitMax3Decimal + " " + decimal2FixedDigits + "$";

  public final static Pattern pattern = Pattern.compile(inputPattern);

  Float weight;
  Float price;

  public static PostalFee parseFee(String input) throws PostalFeeFormatException {
    Matcher matcher = pattern.matcher(input);
    if(matcher.find()) {
      Float weight = parseFloat(matcher.group(1), "fee weight");
      Float price = parseFloat(matcher.group(2), "fee price");

      return new PostalFee(weight, price);
    }
    throw new PostalFeeFormatException(
        "Not valid format for: '" + input + "'. " +
        "Format should be: <positive number><space><positive 2 decimal spaces>");
  }

  private static Float parseFloat(String number, String identifier) throws PostalFeeFormatException {
    try {
      return Float.parseFloat(number);
    } catch (NumberFormatException nfe) {
      throw new PostalFeeFormatException("Not valid number format for: " + identifier);
    }
  }
}
