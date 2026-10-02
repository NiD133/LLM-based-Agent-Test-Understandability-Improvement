package org.apache.commons.cli;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class DefaultParser_ESTest_test25 extends DefaultParser_ESTest_scaffolding {

    /**
     * Parses the short option "-s#", where "s" is a registered option that requires
     * an argument. The parser attaches the trailing "#" to the option as its value,
     * so parsing succeeds and returns a non-null CommandLine.
     */
    @Test(timeout = 4000)
    public void parseShortOptionWithAttachedArgumentSucceeds() throws Throwable {
        // Register a single short option "s" that takes an argument value.
        Options options = new Options().addOption("s", true, "s");

        // Command line supplies "-s#" (option "s" with the attached value "#");
        // the remaining slots are left null, as the original test does.
        String[] arguments = new String[10];
        arguments[0] = "-s#";

        DefaultParser parser = new DefaultParser();
        CommandLine commandLine = parser.parse(options, arguments, false);

        assertNotNull(commandLine);
    }
}
