package com.bsc.postalservice.fee;

public interface PostalFeeService {

  void addFeeEntry(PostalFee postalFee);

  void addFeeEntry(Float weight, Float fee);

  Float getFeeBasedOnWeight(Float packageWeight);

}
