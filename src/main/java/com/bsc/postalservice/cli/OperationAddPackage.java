package com.bsc.postalservice.cli;

import com.bsc.postalservice.postalpackage.domain.PostalPackage;
import com.bsc.postalservice.postalpackage.domain.PostalPackageRepository;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OperationAddPackage implements CLIOperation {

  public static final String PACKAGE_ADDED = "Package added";
  public static final String PACKAGE_WRONG_FORMAT = "Wrong format";

  private final static Pattern pattern = Pattern.compile("^(\\d+(?:\\.\\d{1,3})?) (\\d{5})$");
  private PostalPackageRepository postalPackageRepository;

  public OperationAddPackage(PostalPackageRepository postalPackageRepository) {
    this.postalPackageRepository = postalPackageRepository;
  }

  @Override
  public boolean canExecute(String input) {
    return pattern.matcher(input).matches();
  }

  @Override
  public String execute(String input) {
    PostalPackage postalPackage = parsePostalPackage(input);
    if(postalPackage != null) {
      postalPackageRepository.add(parsePostalPackage(input));
      return PACKAGE_ADDED;
    }
    return PACKAGE_WRONG_FORMAT;
  }

  private PostalPackage parsePostalPackage(String input) {
    Matcher matcher = pattern.matcher(input);
    if(matcher.find()) {
      String weight = matcher.group(1);
      String postalCode = matcher.group(2);
      return new PostalPackage(postalCode, Float.valueOf(weight));
    }
    return null;
  }
}
