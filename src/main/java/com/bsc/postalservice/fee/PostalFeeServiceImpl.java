package com.bsc.postalservice.fee;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Reference implementation of {@link PostalFeeService}
 * This implementation relies on {@link TreeMap} structure which holds keys ordered in natural ascending order.
 */
public class PostalFeeServiceImpl implements PostalFeeService {

  private static final Logger LOG = LoggerFactory.getLogger(PostalFeeServiceImpl.class);

  private Map<Float, Float> weightFeeStructure;

  public PostalFeeServiceImpl() {
    weightFeeStructure = new TreeMap<>();
  }

  /**
   * Adds weight-fee association to structure. If weight is already defined in structure it will be overriden.
   * @param fee associated with weight
   */
  @Override
  public void addFeeEntry(PostalFee fee) {
    if(weightFeeStructure.get(fee.getWeight()) != null) {
      LOG.warn("Fee structure already contains weight: " + fee.getWeight() + " Overriding previous fee.");
    }
    weightFeeStructure.put(fee.getWeight(), fee.getPrice());
  }

  /**
   * This method iterates weights in descending order from weight-fee structure.
   * It looks for first weight that is lower than weight from parameter.
   * If it is then it returns fee associated with that weight.
   * @param packageWeight
   * @return fee based on defined fee structure
   */
  @Override
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
