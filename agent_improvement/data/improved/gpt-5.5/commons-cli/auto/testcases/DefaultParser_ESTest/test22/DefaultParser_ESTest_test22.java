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
public class DefaultParser_ESTest_test22 extends DefaultParser_ESTest_scaffolding {

    private static final String SHORT_OPTION = "js4";
    private static final String EMPTY_LONG_OPTION = "";
    private static final boolean OPTION_ACCEPTS_ARGUMENT = true;
    private static final String OPTION_DESCRIPTION_AND_ARGUMENT_TOKEN = "-=};SP'";
    private static final int ARGUMENT_COUNT = 32;
    private static final int TOKEN_POSITION = 23;
    private static final boolean STOP_AT_NON_OPTION = true;
    private static final boolean ALLOW_PARTIAL_MATCHING = true;

    @Test(timeout = 4000)
    public void test22() throws Throwable {
        Options options = new Options();
        options.addOption(
                SHORT_OPTION,
                EMPTY_LONG_OPTION,
                OPTION_ACCEPTS_ARGUMENT,
                OPTION_DESCRIPTION_AND_ARGUMENT_TOKEN);

        DefaultParser parser = new DefaultParser(ALLOW_PARTIAL_MATCHING);

        String[] arguments = new String[ARGUMENT_COUNT];
        arguments[TOKEN_POSITION] = OPTION_DESCRIPTION_AND_ARGUMENT_TOKEN;

        CommandLine parsedCommandLine = parser.parse(options, arguments, STOP_AT_NON_OPTION);

        assertNotNull(parsedCommandLine);
    }
}
