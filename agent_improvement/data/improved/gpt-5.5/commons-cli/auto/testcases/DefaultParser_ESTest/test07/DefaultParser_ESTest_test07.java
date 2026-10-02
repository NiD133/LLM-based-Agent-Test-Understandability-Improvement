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
public class DefaultParser_ESTest_test07 extends DefaultParser_ESTest_scaffolding {

    private static final String OPTION_NAME = "s";
    private static final String OPTION_TOKEN = "-s";
    private static final String OPTION_VALUE = "-=9Udvb/'--";

    @Test(timeout = 4000)
    public void test07() throws Throwable {
        DefaultParser parser = new DefaultParser();
        Options options = new Options();
        options.addOption(OPTION_NAME, true, OPTION_VALUE);

        String[] commandLineTokens = new String[3];
        commandLineTokens[0] = OPTION_TOKEN;
        commandLineTokens[1] = OPTION_VALUE;

        CommandLine parsedCommandLine = parser.parse(options, commandLineTokens, true);

        assertNotNull(parsedCommandLine);
    }
}
