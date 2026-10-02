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
public class DefaultParser_ESTest_test25 extends DefaultParser_ESTest_scaffolding {

    /**
     * Verifies that parsing a short option with its argument concatenated (e.g., "-s#")
     * succeeds and returns a non-null CommandLine, even when the argument array has
     * trailing null entries.
     */
    @Test(timeout = 4000)
    public void test25() throws Throwable {
        // Define option "s" that accepts an argument
        Options options = new Options();
        options.addOption("s", true, "s");

        // Build an argument array where the first entry combines the option flag
        // and its value in the concatenated form "-s#" (i.e., option "s" with value "#")
        String[] args = new String[10];
        args[0] = "-s#";

        DefaultParser parser = new DefaultParser();
        CommandLine commandLine = parser.parse(options, args, false);

        assertNotNull(commandLine);
    }
}
