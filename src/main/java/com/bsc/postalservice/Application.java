package com.bsc.postalservice;

import com.bsc.postalservice.cli.*;
import com.bsc.postalservice.fee.PostalFeeService;
import com.bsc.postalservice.postalpackage.domain.PostalPackageRepository;
import com.bsc.postalservice.postalpackage.infrastructure.InMemoryPostalPackageRepository;
import com.bsc.postalservice.schedule.ConsoleSummaryWriterJob;
import com.bsc.postalservice.schedule.FixedPeriodJobDetail;
import com.bsc.postalservice.schedule.FixedPeriodJobScheduler;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.RejectedExecutionException;

import static java.util.concurrent.TimeUnit.SECONDS;

public class Application {

  public static void main(String[] args) {
    System.out.println("Hi, this is BSC postal delivery service");

    PostalPackageRepository postalPackageRepository = new InMemoryPostalPackageRepository();
    PostalFeeService postalFeeService = new PostalFeeService();

    List<CLIOperation> packageOperations = Arrays.asList(
        new OperationAddPackage(postalPackageRepository, postalFeeService),
        new OperationQuit()
    );

    List<CLIOperation> feeOperations = Collections.singletonList(
        new OperationAddFee(postalFeeService)
    );

    InputProcessor packageProcessor = new InputProcessor(new OperationsFactory(packageOperations));
    InputProcessor feeProcessor = new InputProcessor(new OperationsFactory(feeOperations));

    FixedPeriodJobScheduler scheduler = new FixedPeriodJobScheduler();
    ConsoleSummaryWriterJob job = new ConsoleSummaryWriterJob(postalPackageRepository);

    try {
      if (args.length >= 1) {
        System.out.println("Processing file input");
        packageProcessor.processInput(new FileInputStream(new File(args[0])), System.out);

        if(args.length == 2) {
          System.out.println("Processing fee structure input");
          feeProcessor.processInput(new FileInputStream(new File(args[1])), System.out);
        }
      }


      scheduler.scheduleJob(job, new FixedPeriodJobDetail(5, 60, SECONDS));

      System.out.println("Processing user input ");
      packageProcessor.processInput(System.in, System.out);

    } catch (FileNotFoundException fnf) {
      System.out.println("Cannot load initial data");
    } catch (TerminateException terminate) {
      System.out.println("Goodbye");
    } catch (RejectedExecutionException se) {
      System.out.println("Unable to run schedule");
    } finally {
      scheduler.shutDown();
    }
  }
}
