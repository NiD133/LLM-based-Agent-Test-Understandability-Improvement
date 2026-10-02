package org.apache.commons.cli;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class DefaultParser_ESTest_test17 extends DefaultParser_ESTest_scaffolding {

    /**
     * When no options are defined and parsing is told to stop at the first
     * non-option token (stopAtNonOption = true), an unrecognized argument such
     * as "-c=wt9" should not raise an exception; the parser should instead
     * return a CommandLine collecting the remaining tokens as arguments.
     */
    @Test(timeout = 4000)
    public void parseUnknownOptionStopsAtNonOptionAndReturnsCommandLine() throws Throwable {
        Options emptyOptions = new Options();
        DefaultParser parser = new DefaultParser();

        // First token is an unrecognized option; second token is left null.
        String[] arguments = new String[2];
        arguments[0] = "-c=wt9";

        boolean stopAtNonOption = true;
        CommandLine commandLine = parser.parse(emptyOptions, arguments, stopAtNonOption);

        assertNotNull(commandLine);
    }
}
