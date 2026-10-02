package org.apache.commons.cli;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class DefaultParser_ESTest_test22 extends DefaultParser_ESTest_scaffolding {

    /**
     * Verifies that parsing succeeds and returns a non-null CommandLine when an
     * argument that is not a recognized option is encountered while
     * stopAtNonOption is enabled. With stopAtNonOption = true, the unrecognized
     * token is accepted (parsing stops at it) rather than triggering an exception.
     */
    @Test(timeout = 4000)
    public void parseWithStopAtNonOptionAcceptsUnrecognizedArgument() throws Throwable {
        // Define an option "js4" that takes an argument; its long option name is empty.
        Options options = new Options();
        options.addOption("js4", "", true, "-=};SP'");

        // Build the argument array. Only index 23 is populated; the remaining
        // entries are null (and are simply skipped during parsing).
        String[] arguments = new String[32];
        arguments[23] = "-=};SP'";

        // Parse with partial matching enabled and stopAtNonOption = true.
        DefaultParser parser = new DefaultParser(true);
        CommandLine commandLine = parser.parse(options, arguments, true);

        assertNotNull(commandLine);
    }
}
