package com.bsc.postalservice.postalpackage;

import java.util.List;

public interface PostalPackageRepository {

    void add(PostalPackage postalPackage);

    void addAll(List<PostalPackage> postalPackages);

    PostalPackageSummary getGroupedSummaryByCode();
}
