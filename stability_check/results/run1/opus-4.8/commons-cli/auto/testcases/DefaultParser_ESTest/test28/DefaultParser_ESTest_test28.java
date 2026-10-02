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
public class DefaultParser_ESTest_test28 extends DefaultParser_ESTest_scaffolding {

    /**
     * When no options are defined and {@code stopAtNonOption} is {@code true},
     * parsing an unrecognized token must not throw. Instead the parser tolerates
     * the unknown argument and still returns a valid (non-null) CommandLine.
     */
    @Test(timeout = 4000)
    public void parseUnknownTokenWithStopAtNonOptionReturnsCommandLine() throws Throwable {
        Options emptyOptions = new Options();
        DefaultParser parser = new DefaultParser();

        // Only the first argument carries an (unrecognized) token; the rest stay null.
        String[] arguments = new String[14];
        arguments[0] = "-s-#\"J";
        boolean stopAtNonOption = true;

        CommandLine commandLine = parser.parse(emptyOptions, arguments, stopAtNonOption);

        assertNotNull(commandLine);
    }
}
