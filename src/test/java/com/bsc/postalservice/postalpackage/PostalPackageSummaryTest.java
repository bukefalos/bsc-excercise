package com.bsc.postalservice.postalpackage;

import com.bsc.postalservice.utils.ThreadWithException;
import org.junit.Before;
import org.junit.Test;

import static com.bsc.postalservice.utils.TestUtils.randomFloat;
import static com.bsc.postalservice.utils.TestUtils.randomSingleNumber;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

public class PostalPackageSummaryTest {

  private PostalPackageSummary summary;

  @Before
  public void init() {
    summary = new PostalPackageSummary();
  }

  @Test
  public void addToSummary() {
    summary.addToSummary(new PostalPackage("04011", 1.0f));
    summary.addToSummary(new PostalPackage("04011", 2.0f));

    assertThat(summary.size(), is(1));
  }

  @Test
  public void getTotalWeight() {
    summary.addToSummary(new PostalPackage("04011", 1.0f));
    summary.addToSummary(new PostalPackage("04011", 2.0f));

    assertThat(summary.getTotalWeight("04011"), is(3.0f));
  }

  @Test
  public void getTotalFee() {
    summary.addToSummary(new PostalPackage("04011", 1.0f, 5.00f));
    summary.addToSummary(new PostalPackage("04011", 2.0f, 7.00f));

    assertThat(summary.getTotalFee("04011"), is(12.0f));
  }

  @Test
  public void toStringWithoutFees() {
    summary.addToSummary(new PostalPackage("04022", 2.0f, 7.00f));
    summary.addToSummary(new PostalPackage("04022", 0.5f, 2.00f));
    summary.addToSummary(new PostalPackage("04011", 1.0f, 5.00f));
    summary.addToSummary(new PostalPackage("04011", 2.0f, 7.00f));

    String expectedSummary = "04011 3.000\n" +
                             "04022 2.500\n";
    assertThat(summary.toString(), is(expectedSummary));
    assertThat(summary.toString(false), is(expectedSummary));
  }

  @Test
  public void toStringWithFees() {
    summary.addToSummary(new PostalPackage("04022", 2.0f, 7.0f));
    summary.addToSummary(new PostalPackage("04022", 0.5f, 2.0f));
    summary.addToSummary(new PostalPackage("04011", 1.0f, 5.0f));
    summary.addToSummary(new PostalPackage("04011", 2.0f, 7.0f));

    String expectedSummary = "04011 3.000 12.00\n" +
                             "04022 2.500 9.00\n";
    assertThat(summary.toString(true), is(expectedSummary));
  }

  @Test
  public void thatSummaryIsThreadSafe() throws InterruptedException {
    Thread updateThread = updateThread();
    ThreadWithException readThread = readThread();
    updateThread.start();
    readThread.start();

    assertThat(readThread.joinAndGetException(), nullValue());
  }

  private Thread updateThread() {
    return new Thread(() -> {
      for(int i = 0; i < 1_000; i ++) {
        summary.addToSummary(new PostalPackage("1234" + randomSingleNumber(), randomFloat(1)));
      }
    });
  }

  private ThreadWithException readThread() {
    return new ThreadWithException(() -> {
      for(int i = 0; i < 1_000; i ++) {
        summary.toString(true);
      }
    });
  }
}