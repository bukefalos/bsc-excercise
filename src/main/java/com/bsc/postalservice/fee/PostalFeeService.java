package com.bsc.postalservice.fee;

/**
 * Any implementation should be able to build fee structure based on weight levels and
 * return fee based on that structure and weight.
 *
 * Fee structure behaviour is up to implementations.
 * For example see reference implementation {@link PostalFeeServiceImpl}
 */
public interface PostalFeeService {

  /**
   * Way of adding new entry to a fee structure.
   * Service should be able to build weight based fee structure levels
   * Usually fee service gets initialized by calling this method multiple times at the beginning
   * @param postalFee
   */
  void addFeeEntry(PostalFee postalFee);

  /**
   * Implementing class has to return fee based on previously defined weight based fee structure
   * @param packageWeight
   * @return fee from defined fee structure
   */
  Float getFeeBasedOnWeight(Float packageWeight);

}
