package com.bsc.postalservice.fee;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class PostalFeeServiceImplTest {

  private PostalFeeService feeService;

  @Before
  public void init() {
    /*
      Fee structure looks like this, for testing purposes it is initialized in arbitrary order

      WEIGHT | FEE
      -------------
      10       5.00
      5        2.50
      3        2.00
      2        1.50
      1        1.00
      0.5      0.70
      0.2      0.50
     */
    feeService = new PostalFeeServiceImpl();
    feeService.addFeeEntry(0.5f, 0.70f);
    feeService.addFeeEntry(2f, 1.50f);
    feeService.addFeeEntry(0.2f, 0.50f);
    feeService.addFeeEntry(1f, 1.00f);
    feeService.addFeeEntry(5f, 2.50f);
    feeService.addFeeEntry(3f, 2.00f);
    feeService.addFeeEntry(10f, 5.00f);
  }

  @Test
  public void getFeeBasedOnWeight() {
    assertThat(feeService.getFeeBasedOnWeight(12.0f), is(5.0f));
    assertThat(feeService.getFeeBasedOnWeight(10.0f), is(5.0f));
    assertThat(feeService.getFeeBasedOnWeight(0.10f), is(0.0f));
  }

  @Test
  public void thatOverrideBySameWeightWorks() {
    feeService.addFeeEntry(10f, 5.5f);
    assertThat(feeService.getFeeBasedOnWeight(12.0f), is(5.5f));
  }

  @Test
  public void thatFeeServiceWorksWithoutAnyEntries() {
    PostalFeeService emptyFeeService = new PostalFeeServiceImpl();
    assertThat(emptyFeeService.getFeeBasedOnWeight(5.0f), is(0.0f));
  }

}