package com.bsc.postalservice;

import com.bsc.postalservice.cli.*;
import com.bsc.postalservice.postalpackage.domain.PostalPackageRepository;
import com.bsc.postalservice.postalpackage.infrastructure.InMemoryPostalPackageRepository;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;

public class Application {

  public static void main(String[] args) {
    System.out.println("Hi, this is BSC postal delivery service");

    //TODO: add read schedule for every minute

    PostalPackageRepository postalPackageRepository = new InMemoryPostalPackageRepository();

    List<CLIOperation> operations = Arrays.asList(
        new OperationAddPackage(postalPackageRepository),
        new OperationQuit()
    );

    OperationsFactory cmdOperationsFactory = new OperationsFactory(operations);
    InputProcessor cmdProcessor = new InputProcessor(cmdOperationsFactory);

    try {
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
    }
  }
}
