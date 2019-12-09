package com.bsc.postalservice.postalpackage;

import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.synchronizedList;

public class InMemoryPostalPackageRepository implements PostalPackageRepository {

  private final List<PostalPackage> postalPackageList;

  public InMemoryPostalPackageRepository() {
    this.postalPackageList = synchronizedList(new ArrayList<>());
  }

  @Override
  public void add(PostalPackage postalPackage) {
    postalPackageList.add(postalPackage);
  }

  @Override
  public void addAll(List<PostalPackage> postalPackages) {
    postalPackageList.addAll(postalPackages);
  }

  @Override
  public PostalPackageSummary getGroupedSummaryByCode() {
    synchronized (postalPackageList) {
      return new PostalPackageSummary(postalPackageList);
    }
  }
}
