package com.bsc.postalservice.cli;

import com.bsc.postalservice.fee.PostalFeeService;
import com.bsc.postalservice.postalpackage.domain.PostalPackage;
import com.bsc.postalservice.postalpackage.domain.PostalPackageFormatException;
import com.bsc.postalservice.postalpackage.domain.PostalPackageRepository;

public class OperationAddPackage implements CLIOperation {

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
