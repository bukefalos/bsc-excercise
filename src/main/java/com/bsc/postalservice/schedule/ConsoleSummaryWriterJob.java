package com.bsc.postalservice.schedule;

import com.bsc.postalservice.postalpackage.domain.PostalPackageRepository;

public class ConsoleSummaryWriterJob implements Job {

  private PostalPackageRepository repository;
  private Boolean includeFees;

  public ConsoleSummaryWriterJob(PostalPackageRepository repository, Boolean includeFees) {
    this.repository = repository;
    this.includeFees = includeFees;
  }

  @Override
  public void execute() throws JobExecutionException {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("\n*** Package summary ***\n");
    stringBuilder.append(repository.getGroupedSummaryByCode().toString(includeFees));
    stringBuilder.append("=== Package summary end ===");
    System.out.println(stringBuilder.toString());
  }
}
