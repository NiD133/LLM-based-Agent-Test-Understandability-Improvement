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
public class DefaultParser_ESTest_test25 extends DefaultParser_ESTest_scaffolding {

    private static final String OPTION_NAME = "s";
    private static final String OPTION_DESCRIPTION = "s";
    private static final String OPTION_TOKEN_WITH_ATTACHED_VALUE = "-s#";
    private static final int ARGUMENT_ARRAY_SIZE = 10;

    @Test(timeout = 4000)
    public void test25() throws Throwable {
        Options options = new Options();
        DefaultParser parser = new DefaultParser();
        String[] commandLineArguments = new String[ARGUMENT_ARRAY_SIZE];
        commandLineArguments[0] = OPTION_TOKEN_WITH_ATTACHED_VALUE;

        Options optionsWithArgumentOption = options.addOption(OPTION_NAME, true, OPTION_DESCRIPTION);
        CommandLine parsedCommandLine = parser.parse(optionsWithArgumentOption, commandLineArguments, false);

        assertNotNull(parsedCommandLine);
    }
}
