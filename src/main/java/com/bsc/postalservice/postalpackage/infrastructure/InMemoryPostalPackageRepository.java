package com.bsc.postalservice.postalpackage.infrastructure;

import com.bsc.postalservice.postalpackage.domain.PostalPackage;
import com.bsc.postalservice.postalpackage.domain.PostalPackageRepository;
import com.bsc.postalservice.postalpackage.domain.PostalPackageSummary;

import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.synchronizedList;
import static java.util.stream.Collectors.toMap;

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
      return postalPackageList.stream()
          .collect(toMap(PostalPackage::getPostalCode, PostalPackage::getWeight, Float::sum, PostalPackageSummary::new));
    }
  }
}
