package com.bsc.postalservice.postalpackage;

import com.bsc.postalservice.utils.ThreadWithException;
import lombok.val;
import org.junit.Before;
import org.junit.Test;

import static com.bsc.postalservice.utils.TestUtils.randomFloat;
import static com.bsc.postalservice.utils.TestUtils.randomSingleNumber;
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

    val summary = postalPackageRepository.getAll();
    assertThat(summary.size(), is(5));
    assertThat(summary.get(0).getPostalCode(), is("04011"));
    assertThat(summary.get(0).getWeight(), is(1.1f));
  }

  @Test
  public void addAll() {
    val postalPackages = asList(
        new PostalPackage("04011", 1.1f),
        new PostalPackage("04022", 2.2f),
        new PostalPackage("04022", 3.3f),
        new PostalPackage("04033", 5.5f),
        new PostalPackage("04011", 6.6f)
    );
    postalPackageRepository.addAll(postalPackages);
    val summary = postalPackageRepository.getAll();
    assertThat(summary.size(), is(5));
    assertThat(summary.get(0).getPostalCode(), is("04011"));
    assertThat(summary.get(0).getWeight(), is(1.1f));
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
        postalPackageRepository.add(new PostalPackage("1234" + randomSingleNumber(), randomFloat(1)));
      }
    });
  }

  private ThreadWithException readThread() {
    return new ThreadWithException(() -> {
      for(int i = 0; i < 10_000; i ++) {
        postalPackageRepository.getAll();
      }
    });
  }
}