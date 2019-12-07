package com.bsc.postalservice.postalpackage.domain;

import lombok.Value;

@Value
public class PostalPackage {
    String postalCode;
    Float weight;
}
