package com.bsc.postalservice.utils;

public class TestUtils {

  public static String randomSingleNumber() {
    return String.valueOf(Math.random() * 9 + 1);
  }

  public static Float randomFloat(Integer order) {
    return (float) Math.random() * (float) Math.pow(10, order);
  }
}
