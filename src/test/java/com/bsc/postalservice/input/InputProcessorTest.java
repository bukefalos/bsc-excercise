package com.bsc.postalservice.input;

import com.bsc.postalservice.postalpackage.InMemoryPostalPackageRepository;
import com.bsc.postalservice.postalpackage.PostalPackageRepository;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;

public class InputProcessorTest {

  private InputProcessor inputProcessor;

  @Before
  public void init() {
    PostalPackageRepository repo = new InMemoryPostalPackageRepository();
    OperationsFactory operationsFactory = new OperationsFactory(
        Arrays.asList(new OperationAddPackage(repo), new OperationQuit())
    );

    inputProcessor = new InputProcessor(operationsFactory);
  }

  @Test
  public void processCorrectInput() {
    ByteArrayOutputStream output = new ByteArrayOutputStream();
    String[] inputLines = {
        "123 12345",
        "12.456 66643",
        "4.67 66666",
        "quit"
    };

    try {
      inputProcessor.processInput(
          input(inputLines),
          new PrintStream(output)
      );
    } catch (TerminateException term) {
      String outputString = output.toString();
      assertThat(outputString, containsString(OperationAddPackage.PACKAGE_ADDED));
      assertFalse(outputString.contains(OperationAddPackage.PACKAGE_WRONG_FORMAT));
      assertFalse(outputString.contains(OperationUnrecognized.UNRECOGNIZED_FORMAT_MSG_WITH_HELP));
    }

  }

  private ByteArrayInputStream input(String... lines) {
    StringBuilder builder = new StringBuilder();
    Arrays.asList(lines).forEach(line -> {
      builder.append(line);
      builder.append("\n");
    });
    return new ByteArrayInputStream(builder.toString().getBytes());
  }


}