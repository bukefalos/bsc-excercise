package com.bsc.postalservice.fee;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class PostalFeeService {

  private static final Logger LOG = LoggerFactory.getLogger(PostalFeeService.class);

  private Map<Float, Float> weightFeeStructure;

  public PostalFeeService() {
    weightFeeStructure = new TreeMap<>();
  }

  public void addFeeEntry(PostalFee postalFee) {
    this.addFeeEntry(postalFee.getWeight(), postalFee.getPrice());
  }

  public void addFeeEntry(Float weight, Float fee) {
    if(weightFeeStructure.get(weight) != null) {
      LOG.warn("Fee structure already contains weight: " + weight + " Overriding previous fee.");
    }
    weightFeeStructure.put(weight, fee);
  }

  public Float getFeeBasedOnWeight(Float packageWeight) {
    // treemap's keys are in ascending natural order
    List<Float> weights = new ArrayList<> (weightFeeStructure.keySet());
    Collections.reverse(weights);
    return weights.stream()
        .filter(weight -> packageWeight >=weight)
        .findFirst()
        .map(weightFeeStructure::get)
        .orElse(0.0f);
  }
}
