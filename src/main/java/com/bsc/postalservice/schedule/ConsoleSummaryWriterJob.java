package com.bsc.postalservice.schedule;

import com.bsc.postalservice.postalpackage.PostalPackageRepository;
import com.bsc.postalservice.postalpackage.PostalPackageSummary;

import java.io.PrintStream;

public class ConsoleSummaryWriterJob implements Job {

  public static final String PACKAGE_SUMMARY_HEADER = "\n*** Package summary ***\n";
  public static final String PACKAGE_SUMMARY_FOOTER = "=== Package summary end ===";

  private PostalPackageRepository repository;
  private Boolean includeFees;
  private PrintStream writer;

  public ConsoleSummaryWriterJob(PostalPackageRepository repository, Boolean includeFees, PrintStream writer) {
    this.repository = repository;
    this.includeFees = includeFees;
    this.writer = writer;
  }

  public ConsoleSummaryWriterJob(PostalPackageRepository repository, Boolean includeFees) {
    this(repository, includeFees, System.out);
  }

  @Override
  public void execute() throws JobExecutionException {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(PACKAGE_SUMMARY_HEADER);
    stringBuilder.append(new PostalPackageSummary(repository.getAll()).toString(includeFees));
    stringBuilder.append(PACKAGE_SUMMARY_FOOTER);
    writer.println(stringBuilder.toString());
  }
}
