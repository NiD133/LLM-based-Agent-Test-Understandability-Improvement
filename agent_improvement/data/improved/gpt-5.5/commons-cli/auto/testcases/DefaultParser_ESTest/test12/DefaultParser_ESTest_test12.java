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
public class DefaultParser_ESTest_test12 extends DefaultParser_ESTest_scaffolding {

    private static final String SINGLE_HYPHEN_TOKEN = "-";

    @Test(timeout = 4000)
    public void test12() throws Throwable {
        DefaultParser parser = new DefaultParser();
        Options emptyOptions = new Options();
        String[] arguments = new String[1];
        arguments[0] = SINGLE_HYPHEN_TOKEN;

        CommandLine parsedCommandLine = parser.parse(emptyOptions, arguments, true);

        assertNotNull(parsedCommandLine);
    }
}
