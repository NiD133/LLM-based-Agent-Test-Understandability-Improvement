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
public class DefaultParser_ESTest_test16 extends DefaultParser_ESTest_scaffolding {

    private static final String REQUIRED_OPTION = "c";
    private static final String REQUIRED_OPTION_TOKEN = "-c=wt9";
    private static final String REQUIRED_OPTION_DESCRIPTION = "-c";

    @Test(timeout = 4000)
    public void test16() throws Throwable {
        Options options = new Options();
        Options optionsWithRequiredValue = options.addRequiredOption(
                REQUIRED_OPTION,
                REQUIRED_OPTION_TOKEN,
                true,
                REQUIRED_OPTION_DESCRIPTION);

        DefaultParser parser = new DefaultParser();
        String[] arguments = new String[2];
        arguments[0] = REQUIRED_OPTION_TOKEN;

        CommandLine commandLine = parser.parse(optionsWithRequiredValue, arguments, true);

        assertNotNull(commandLine);
    }
}
