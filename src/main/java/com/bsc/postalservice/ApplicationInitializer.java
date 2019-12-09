package com.bsc.postalservice;

import com.bsc.postalservice.cli.*;
import com.bsc.postalservice.fee.PostalFeeService;
import com.bsc.postalservice.postalpackage.domain.PostalPackageRepository;
import com.bsc.postalservice.schedule.ConsoleSummaryWriterJob;
import com.bsc.postalservice.schedule.Job;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

class ApplicationInitializer {

  private static final Logger LOG = LoggerFactory.getLogger(ApplicationInitializer.class);

  static InputProcessor feeInputProcessor(PostalFeeService feeService) {
    List<CLIOperation> feeOperations = Collections.singletonList(
        new OperationAddFee(feeService)
    );
    return new InputProcessor(new OperationsFactory(feeOperations));
  }
  static void processFees(InputProcessor feeProcessor, String file) throws FileNotFoundException, TerminateException {
    LOG.info("Processing fees file input");
    feeProcessor.processInput(new FileInputStream(new File(file)), System.out);
  }

  static InputProcessor postalPackageInputProcessor(PostalPackageRepository repository, PostalFeeService feeService) {
    List<CLIOperation> packageOperations = Arrays.asList(
        new OperationAddPackage(repository, feeService),
        new OperationQuit()
    );
    return new InputProcessor(new OperationsFactory(packageOperations));
  }

  static void processInitialPackages(InputProcessor inputProcessor, String file) throws FileNotFoundException, TerminateException {
    LOG.info("Processing initial package file input");
    inputProcessor.processInput(new FileInputStream(new File(file)), System.out);
  }

  static Job createConsoleSummaryJob(PostalPackageRepository repository, boolean includeFees) {
    Objects.requireNonNull(repository, "You need to initialize repository first");
    return new ConsoleSummaryWriterJob(repository, includeFees);
  }

}
