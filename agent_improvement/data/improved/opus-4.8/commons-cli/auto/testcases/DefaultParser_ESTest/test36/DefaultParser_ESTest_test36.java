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
public class DefaultParser_ESTest_test36 extends DefaultParser_ESTest_scaffolding {

    /**
     * Parsing an unknown short-option token ("-s") with stopAtNonOption enabled
     * should not throw: parsing stops at the unrecognized token and still yields
     * a CommandLine. The argument array also contains trailing null entries, which
     * the parser tolerates.
     */
    @Test(timeout = 4000)
    public void parseUnknownOptionStopsAtNonOptionAndReturnsCommandLine() throws Throwable {
        Options emptyOptions = new Options();
        DefaultParser parser = new DefaultParser();

        // First token is an unknown option; remaining six entries are null.
        String[] arguments = new String[7];
        arguments[0] = "-s";

        boolean stopAtNonOption = true;
        CommandLine commandLine = parser.parse(emptyOptions, arguments, stopAtNonOption);

        assertNotNull(commandLine);
    }
}
