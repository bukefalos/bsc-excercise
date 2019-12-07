package com.bsc.postalservice;

import com.bsc.postalservice.cli.*;
import com.bsc.postalservice.postalpackage.domain.PostalPackageRepository;
import com.bsc.postalservice.postalpackage.infrastructure.InMemoryPostalPackageRepository;

import java.util.Arrays;
import java.util.List;

public class Application {

    public static void main(String[] args) {
        System.out.println("Hi, this is BSC postal delivery service");

        //TODO: read file from arg, parse and load initial data
        //TODO: add read schedule for every minute

        PostalPackageRepository postalPackageRepository = new InMemoryPostalPackageRepository();

        List<CLIOperation> operations = Arrays.asList(
            new OperationAddPackage(postalPackageRepository),
            new OperationQuit()
        );

        OperationsFactory cmdOperationsFactory = new OperationsFactory(operations);
        InputProcessor processor = new InputProcessor(cmdOperationsFactory);

        try {
            processor.processInput(System.in, System.out);
        } catch (TerminateException terminate) {
            System.out.println("Goodbye");
        }
    }
}
