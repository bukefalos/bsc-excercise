package com.bsc.postalservice;

import com.bsc.postalservice.cli.*;
import com.bsc.postalservice.fee.PostalFeeService;
import com.bsc.postalservice.postalpackage.PostalPackageRepository;
import com.bsc.postalservice.schedule.ConsoleSummaryWriterJob;
import com.bsc.postalservice.schedule.Job;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static com.bsc.postalservice.Application.OPTION_FEE_STRUCTURE;
import static com.bsc.postalservice.Application.OPTION_INIT_PACKAGES;

class ApplicationInitializer {

  private static final Logger LOG = LoggerFactory.getLogger(ApplicationInitializer.class);

  static Options createCLIOptions() {
    Option initialPackagesFile = initialPackagesOption();
    Option initialFeesFile = initialFeesOption();

    Options options = new Options();
    options.addOption(initialPackagesFile);
    options.addOption(initialFeesFile);
    return options;
  }

  private static Option initialFeesOption() {
    return Option.builder(OPTION_FEE_STRUCTURE)
        .argName("fee file path")
        .longOpt("fees")
        .optionalArg(true)
        .hasArg()
        .numberOfArgs(1)
        .argName("FILE")
        .valueSeparator()
        .desc("Define fee structure file input. Format of file should for each line:\n" +
            "<weight><space><fee>\n" +
            "weight: integer|decimal(up to 3 decimal spaces)\n" +
            "fee: decimal(fixed 2 decimal spaces)")
        .build();
  }

  private static Option initialPackagesOption() {
    return Option.builder(OPTION_INIT_PACKAGES)
        .argName("init file path")
        .longOpt("init")
        .optionalArg(true)
        .hasArg()
        .numberOfArgs(1)
        .argName("FILE")
        .valueSeparator()
        .desc("Define initial packages file input. Format of file should for each line:" +
            "<weight><space><postal_code>\n" +
            "weight: integer|decimal(up to 3 decimal spaces)\n" +
            "postal_code: integer-char(5 digits)")
        .build();
  }


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
