package com.bsc.postalservice.schedule;

import com.bsc.postalservice.postalpackage.PostalPackage;
import com.bsc.postalservice.postalpackage.PostalPackageRepository;
import com.bsc.postalservice.postalpackage.PostalPackageSummary;
import lombok.val;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;

import static com.bsc.postalservice.schedule.ConsoleSummaryWriterJob.PACKAGE_SUMMARY_FOOTER;
import static com.bsc.postalservice.schedule.ConsoleSummaryWriterJob.PACKAGE_SUMMARY_HEADER;
import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ConsoleSummaryWriterJobTest {

  private ConsoleSummaryWriterJob summaryWriterJob;
  private ByteArrayOutputStream writer;

  @Mock
  private PostalPackageRepository repository;

  @Before
  public void init() {
    MockitoAnnotations.initMocks(repository);
    writer = new ByteArrayOutputStream();
    summaryWriterJob = new ConsoleSummaryWriterJob(repository, true, new PrintStream(writer));
  }

  @Test
  public void execute() throws JobExecutionException {
    val packages =  Arrays.asList(
        new PostalPackage("04011", 10f, 2.0f),
        new PostalPackage("04011", 11f, 2.50f));

    when(repository.getAll()).thenReturn(packages);

    summaryWriterJob.execute();

    assertThat(writer.toString(), containsString(PACKAGE_SUMMARY_HEADER));
    assertThat(writer.toString(), containsString(PACKAGE_SUMMARY_FOOTER));
    assertThat(writer.toString(), containsString(new PostalPackageSummary(packages).toString(true)));
  }
}