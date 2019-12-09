package com.bsc.postalservice.postalpackage.domain;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PostalPackageSummary {

  Map<String, PostalPackage> summary;

  public PostalPackageSummary() {
    summary = new HashMap<>();
  }

  public PostalPackageSummary(List<PostalPackage> postalPackages)  {
    this();
    postalPackages.forEach(this::addToSummary);
  }

  public void addToSummary(PostalPackage newPostalPackage) {
    PostalPackage postalCodeSummary = summary.get(newPostalPackage.getPostalCode());
    if (postalCodeSummary != null) {
      Float summaryWeight = postalCodeSummary.getWeight() + newPostalPackage.getWeight();
      Float summaryFee = postalCodeSummary.getFee() + newPostalPackage.getFee();
      summary.put(postalCodeSummary.getPostalCode(), postalCodeSummary.withWeight(summaryWeight).withFee(summaryFee));
    } else {
      summary.put(newPostalPackage.getPostalCode(), newPostalPackage);
    }
  }

  public Integer size() {
    return summary.size();
  }

  public Float getTotalWeight(String postalCode) {
    PostalPackage postalCodeSummary = summary.get(postalCode);
    return postalCodeSummary != null
        ? postalCodeSummary.getWeight()
        : 0.0f;
  }

  public Float getTotalFee(String postalCode) {
    PostalPackage postalCodeSummary = summary.get(postalCode);
    return postalCodeSummary != null
        ? postalCodeSummary.getFee()
        : 0.0f;
  }

  @Override
  public String toString() {
    return toString(false);
  }

  public String toString(boolean includeFees) {
    StringBuilder stringBuilder = new StringBuilder();
    summary.forEach((key, postalCodeSummary) -> {
      stringBuilder.append(key);
      stringBuilder.append(" ");
      stringBuilder.append(postalCodeSummary.getWeight());
      if(includeFees) {
        stringBuilder.append(" ");
        stringBuilder.append(postalCodeSummary.getFee());
      }
      stringBuilder.append("\n");
    });
    return stringBuilder.toString();
  }
}
