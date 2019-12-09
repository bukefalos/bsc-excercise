package com.bsc.postalservice.schedule;

import com.bsc.postalservice.postalpackage.domain.PostalPackageRepository;

public class ConsoleSummaryWriterJob implements Job {

  private PostalPackageRepository repository;

  public ConsoleSummaryWriterJob(PostalPackageRepository repository) {
    this.repository = repository;
  }

  @Override
  public void execute() throws JobExecutionException {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("\n*** Package summary ***\n");
    stringBuilder.append(repository.getGroupedSummaryByCode());
    stringBuilder.append("=== Package summary end ===");
    System.out.println(stringBuilder.toString());
  }
}
