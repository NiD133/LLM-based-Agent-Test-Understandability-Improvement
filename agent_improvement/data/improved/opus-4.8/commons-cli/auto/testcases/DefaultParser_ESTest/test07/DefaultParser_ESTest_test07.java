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

    /**
     * Parses a short option "-s" that requires an argument, supplying the
     * argument as the next token. Parsing should succeed and yield a
     * non-null CommandLine, even when stopAtNonOption is enabled.
     */
    @Test(timeout = 4000)
    public void test07() throws Throwable {
        DefaultParser parser = new DefaultParser();

        // Define a single short option "-s" that takes an argument value.
        String argumentValue = "-=9Udvb/'--";
        Options options = new Options();
        options.addOption("s", true, argumentValue);

        // Command line: "-s <value>" with a trailing null token.
        String[] arguments = new String[3];
        arguments[0] = "-s";
        arguments[1] = argumentValue;

        boolean stopAtNonOption = true;
        CommandLine commandLine = parser.parse(options, arguments, stopAtNonOption);

        assertNotNull(commandLine);
    }
}
