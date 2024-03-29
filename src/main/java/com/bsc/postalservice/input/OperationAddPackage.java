package com.bsc.postalservice.input;

import com.bsc.postalservice.fee.PostalFeeService;
import com.bsc.postalservice.postalpackage.PostalPackage;
import com.bsc.postalservice.postalpackage.PostalPackageFormatException;
import com.bsc.postalservice.postalpackage.PostalPackageRepository;

public class OperationAddPackage implements InputOperation {

  public static final String PACKAGE_ADDED = "Package added";
  public static final String PACKAGE_WRONG_FORMAT = "Wrong format";

  private PostalPackageRepository postalPackageRepository;
  private PostalFeeService postalFeeService;

  public OperationAddPackage(PostalPackageRepository postalPackageRepository) {
    this.postalPackageRepository = postalPackageRepository;
    this.postalFeeService = null;
  }

  public OperationAddPackage(
      PostalPackageRepository postalPackageRepository,
      PostalFeeService postalFeeService) {
    this.postalPackageRepository = postalPackageRepository;
    this.postalFeeService = postalFeeService;
  }

  @Override
  public boolean canExecute(String input) {
    return PostalPackage.pattern.matcher(input).matches();
  }

  @Override
  public String execute(String input) {
    try {
      PostalPackage postalPackage = PostalPackage.parsePostalPackage(input);
      postalPackageRepository.add(postalFeeService != null
          ? postalPackage.withFee(postalFeeService.getFeeBasedOnWeight(postalPackage.getWeight()))
          : postalPackage
      );
      return PACKAGE_ADDED;
    } catch (PostalPackageFormatException ppfe) {
      return PACKAGE_WRONG_FORMAT + " " + ppfe.getMessage();
    }
  }
}
