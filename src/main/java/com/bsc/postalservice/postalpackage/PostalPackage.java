package com.bsc.postalservice.postalpackage;

import lombok.AllArgsConstructor;
import lombok.Value;
import lombok.With;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Value
@With
@AllArgsConstructor
public class PostalPackage {

  private static String digitMax3Decimal = "(\\d+(?:\\.\\d{1,3})?)";
  private static String fixed5Numbers = "(\\d{5})";
  private static String inputPattern = "^" + digitMax3Decimal + " " + fixed5Numbers + "$";

  public final static Pattern pattern = Pattern.compile(inputPattern);

  String postalCode;
  Float weight;
  Float fee;

  public PostalPackage(String postalCode, Float weight) {
    this.postalCode = postalCode;
    this.weight = weight;
    this.fee = 0.0f;
  }

  public static PostalPackage parsePostalPackage(String input) throws PostalPackageFormatException{
    Matcher matcher = pattern.matcher(input);
    if (matcher.find()) {
      Float packageWeight = parseFloat(matcher.group(1), "package weight");
      String postalCode = matcher.group(2);

      return new PostalPackage(
          postalCode,
          packageWeight);
    }
    throw new PostalPackageFormatException(
        "Not valid package format for: '" + input + "'." +
        "Format should be: <positive number max 3 decimal><space><5 digits>");
  }

  private static Float parseFloat(String number, String identifier) throws PostalPackageFormatException {
    try {
      return Float.parseFloat(number);
    } catch (NumberFormatException nfe) {
      throw new PostalPackageFormatException("Not valid number format for: " + identifier);
    }
  }
}
