package org.apache.commons.cli;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.util.Properties;
import java.util.function.Consumer;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class DefaultParser_ESTest_test20 extends DefaultParser_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test20() throws Throwable {
        Options options = new Options();
        Options optionsWithRequiredS4 = options.addRequiredOption("s4", "s4", false, "--s");

        String[] argumentsWithPartialLongOption = new String[3];
        argumentsWithPartialLongOption[2] = "--s";

        DefaultParser parser = new DefaultParser();
        CommandLine parsedCommandLine = parser.parse(optionsWithRequiredS4, argumentsWithPartialLongOption, false);

        assertNotNull(parsedCommandLine);
    }
}
