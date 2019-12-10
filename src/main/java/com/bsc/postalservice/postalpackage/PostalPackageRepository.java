package com.bsc.postalservice.postalpackage;

import java.util.List;

/**
 * Repository which holds and is able to return registered postal packages
 */
public interface PostalPackageRepository {

    /**
     * Add new package to repository
     * @param postalPackage
     */
    void add(PostalPackage postalPackage);

    /**
     * Convenience way of adding several packages at once in a batch
     * @param postalPackages
     */
    void addAll(List<PostalPackage> postalPackages);

    /**
     * Returns all registered packages in repository
     * @return all postal packages
     */
    List<PostalPackage> getAll();
}
