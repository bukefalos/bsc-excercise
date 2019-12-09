package com.bsc.postalservice.postalpackage.infrastructure;

import com.bsc.postalservice.postalpackage.InMemoryPostalPackageRepository;
import com.bsc.postalservice.postalpackage.PostalPackage;
import com.bsc.postalservice.postalpackage.PostalPackageRepository;
import com.bsc.postalservice.utils.ThreadWithException;
import lombok.val;
import org.junit.Before;
import org.junit.Test;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

public class InMemoryPostalPackageRepositoryTest {

  private PostalPackageRepository postalPackageRepository;

  @Before
  public void init() {
    postalPackageRepository = new InMemoryPostalPackageRepository();
  }

  @Test
  public void add() {
    postalPackageRepository.add(new PostalPackage("04011", 1.1f));
    postalPackageRepository.add(new PostalPackage("04022", 2.2f));
    postalPackageRepository.add(new PostalPackage("04022", 3.3f));
    postalPackageRepository.add(new PostalPackage("04033", 5.5f));
    postalPackageRepository.add(new PostalPackage("04011", 6.6f));

    val summary =  postalPackageRepository.getGroupedSummaryByCode();
    assertThat(summary.size(), is(3));
    assertThat(summary.getTotalWeight("04011"), is(7.7f));
    assertThat(summary.getTotalWeight("04022"), is(5.5f));
    assertThat(summary.getTotalWeight("04033"), is(5.5f));
  }

  @Test
  public void addAll() {
    val postalPackages = asList(new PostalPackage("04011", 1.1f),
        new PostalPackage("04022", 2.2f),
        new PostalPackage("04022", 3.3f),
        new PostalPackage("04033", 5.5f),
        new PostalPackage("04011", 6.6f)
    );
    postalPackageRepository.addAll(postalPackages);
    val summary = postalPackageRepository.getGroupedSummaryByCode();
    assertThat(summary.size(), is(3));
    assertThat(summary.getTotalWeight("04011"), is(7.7f));
    assertThat(summary.getTotalWeight("04022"), is(5.5f));
    assertThat(summary.getTotalWeight("04033"), is(5.5f));
  }

  @Test
  public void thatReadWhileWriteIsThreadSafe() throws InterruptedException {
    Thread updateThread = updateThread();
    ThreadWithException readThread = readThread();
    updateThread.start();
    readThread.start();

    assertThat(readThread.joinAndGetException(), nullValue());
  }

  private Thread updateThread() {
   return new Thread(() -> {
      for(int i = 0; i < 10_000; i ++) {
        postalPackageRepository.add(new PostalPackage("1234" + randomNumAsString(), randomFloat()));
      }
    });
  }

  private ThreadWithException readThread() {
    return new ThreadWithException(() -> {
      for(int i = 0; i < 10_000; i ++) {
        postalPackageRepository.getGroupedSummaryByCode();
      }
    });
  }

  private String randomNumAsString() {
    return String.valueOf(Math.random() * 9 + 1);
  }

  private Float randomFloat() {
    return (float) Math.random() * 10;
  }
}