package com.bsc.postalservice;

import com.bsc.postalservice.cli.InputProcessor;
import com.bsc.postalservice.cli.TerminateException;
import com.bsc.postalservice.fee.PostalFeeService;
import com.bsc.postalservice.fee.PostalFeeServiceImpl;
import com.bsc.postalservice.postalpackage.InMemoryPostalPackageRepository;
import com.bsc.postalservice.schedule.FixedPeriodJobDetail;
import com.bsc.postalservice.schedule.FixedPeriodJobScheduler;
import com.bsc.postalservice.schedule.Job;
import lombok.val;
import org.apache.commons.cli.*;

import java.io.FileNotFoundException;

import static com.bsc.postalservice.ApplicationInitializer.*;
import static java.util.concurrent.TimeUnit.SECONDS;

public class Application {

  static final String OPTION_INIT_PACKAGES = "I";
  static final String OPTION_FEE_STRUCTURE = "F";

  public static void main(String[] args) {
    System.out.println("Hi, this is BSC postal delivery service");

    Options options = createCLIOptions();
    CommandLineParser parser = new DefaultParser();
    FixedPeriodJobScheduler scheduler = new FixedPeriodJobScheduler();

    try {
      CommandLine line = parser.parse(options, args);

      val repository = new InMemoryPostalPackageRepository();
      val feeService = new PostalFeeServiceImpl();
      val inputProcessor = postalPackageInputProcessor(repository, feeService);

      processFeesOption(line, feeService);
      processInitialPackagesOption(line, inputProcessor);
      scheduleJob(scheduler, createConsoleSummaryJob(repository, line.hasOption(OPTION_FEE_STRUCTURE)));

      System.out.println("Processing user input ");
      inputProcessor.processInput(System.in, System.out);
    } catch (ParseException exp) {
      System.err.println("Parsing failed.  Reason: " + exp.getMessage());
      HelpFormatter formatter = new HelpFormatter();
      formatter.printHelp("bsc", options, true);
    } catch (FileNotFoundException fnf) {
      System.err.println("Not able to load initial file");
    } catch (TerminateException terminate) {
      System.out.println("Goodbye");
    } finally {
      scheduler.shutDown();
    }
  }

  private static void scheduleJob(FixedPeriodJobScheduler scheduler, Job job) {
    scheduler.scheduleJob(job, new FixedPeriodJobDetail(5, 60, SECONDS));
  }

  private static void processFeesOption(CommandLine line, PostalFeeService feeService) throws FileNotFoundException, TerminateException {
    if (line.hasOption(OPTION_FEE_STRUCTURE)) {
      processFees(
          feeInputProcessor(feeService),
          line.getOptionValue(OPTION_FEE_STRUCTURE));
    }
  }

  private static void processInitialPackagesOption(CommandLine line, InputProcessor processor) throws FileNotFoundException, TerminateException {
    if (line.hasOption(OPTION_INIT_PACKAGES)) {
      processInitialPackages(processor, line.getOptionValue(OPTION_INIT_PACKAGES));
    }
  }
}
