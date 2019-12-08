package com.bsc.postalservice;

import com.bsc.postalservice.cli.*;
import com.bsc.postalservice.postalpackage.domain.PostalPackageRepository;
import com.bsc.postalservice.postalpackage.infrastructure.InMemoryPostalPackageRepository;
import com.bsc.postalservice.schedule.SimpleJobScheduler;
import org.quartz.JobDataMap;
import org.quartz.SchedulerException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;

import static com.bsc.postalservice.schedule.ConsoleSummaryWriterJob.createJobDefinition;
import static com.bsc.postalservice.schedule.ConsoleSummaryWriterJob.createTriggerDefinition;

public class Application {

  public static void main(String[] args) {
    System.out.println("Hi, this is BSC postal delivery service");

    PostalPackageRepository postalPackageRepository = new InMemoryPostalPackageRepository();

    List<CLIOperation> operations = Arrays.asList(
        new OperationAddPackage(postalPackageRepository),
        new OperationQuit()
    );

    OperationsFactory cmdOperationsFactory = new OperationsFactory(operations);
    InputProcessor cmdProcessor = new InputProcessor(cmdOperationsFactory);

    SimpleJobScheduler scheduler = new SimpleJobScheduler();

    JobDataMap jobDataMap = new JobDataMap();
    jobDataMap.put("repository", postalPackageRepository);

    try {
      scheduler.scheduleJob(createJobDefinition(jobDataMap), createTriggerDefinition());

      if (args.length == 1) {
        System.out.println("Processing file input");
        cmdProcessor.processInput(new FileInputStream(new File(args[0])), System.out);
      }

      System.out.println("Processing user input ");
      cmdProcessor.processInput(System.in, System.out);

    } catch (FileNotFoundException fnf) {
      System.out.println("Cannot load initial data");
    } catch (TerminateException terminate) {
      System.out.println("Goodbye");
    } catch (SchedulerException se) {
      System.out.println("Unable to run scheduler");
    } finally {
      scheduler.shutDown();
    }
  }
}
