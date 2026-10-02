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
public class DefaultParser_ESTest_test27 extends DefaultParser_ESTest_scaffolding {

    private static final int ARGUMENT_COUNT = 9;
    private static final int UNKNOWN_OPTION_INDEX = 2;
    private static final String UNKNOWN_OPTION_TOKEN = "-s#";

    @Test(timeout = 4000)
    public void test27() throws Throwable {
        Options emptyOptions = new Options();
        DefaultParser parser = new DefaultParser();
        String[] argumentsWithUnknownOption = new String[ARGUMENT_COUNT];
        argumentsWithUnknownOption[UNKNOWN_OPTION_INDEX] = UNKNOWN_OPTION_TOKEN;

        try {
            parser.parse(emptyOptions, argumentsWithUnknownOption, false);
            fail("Expecting exception: Exception");
        } catch (Exception e) {
            //
            // Unrecognized option: -s#
            //
            verifyException("org.apache.commons.cli.DefaultParser", e);
        }
    }
}
