package com.bsc.postalservice.postalpackage.domain;

import java.util.HashMap;

public class PostalPackageSummary extends HashMap<String, Float> {

  public Float getTotalWeight() {
    return (float) values().stream().mapToDouble(value -> value).sum();
  }

  @Override
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder();
    forEach((key, value) -> {
      stringBuilder.append(key);
      stringBuilder.append(" ");
      stringBuilder.append(value);
      stringBuilder.append("\n");
    });
    return stringBuilder.toString();
  }
}
