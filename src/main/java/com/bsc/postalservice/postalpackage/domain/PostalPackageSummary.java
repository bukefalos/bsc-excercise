package com.bsc.postalservice.postalpackage.domain;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Comparator.comparing;
import static java.util.Comparator.reverseOrder;

public class PostalPackageSummary {

  private Map<String, PostalPackage> summary;

  private DecimalFormat weightFormat = new DecimalFormat("0.000");
  private DecimalFormat feeFormat = new DecimalFormat("0.00");

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
    orderByWeight().forEach((key, postalCodeSummary) -> {
      stringBuilder.append(key);
      stringBuilder.append(" ");
      stringBuilder.append(weightFormat.format(postalCodeSummary.getWeight()));
      if(includeFees) {
        stringBuilder.append(" ");
        stringBuilder.append(feeFormat.format(postalCodeSummary.getFee()));
      }
      stringBuilder.append("\n");
    });
    return stringBuilder.toString();
  }

  private HashMap<String, PostalPackage> orderByWeight() {
    return summary.entrySet()
        .stream()
        .sorted(comparing(summary -> summary.getValue().getWeight(), reverseOrder()))
        .collect(Collectors.toMap(
            Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
  }
}
