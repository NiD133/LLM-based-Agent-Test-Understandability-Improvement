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
public class DefaultParser_ESTest_test30 extends DefaultParser_ESTest_scaffolding {

    /**
     * Verifies that parsing succeeds when a required long option ("--s4") appears
     * at a non-zero index within a sparse argument array (most entries are null).
     * The parser is configured with partial matching disabled, so only exact long
     * option names are matched.
     */
    @Test(timeout = 4000)
    public void test30() throws Throwable {
        // Register "s4" as a required option that takes no argument
        Options options = new Options();
        options.addRequiredOption("s4", "s4", false, "--s4");

        // Disable partial matching so only exact long option names are accepted
        DefaultParser parser = new DefaultParser(false);

        // Build a sparse argument array; only index 1 carries the required "--s4" token
        String[] args = new String[6];
        args[1] = "--s4";

        // Parse with stopAtNonOption=false; the required option must be satisfied
        CommandLine commandLine = parser.parse(options, args, false);

        assertNotNull(commandLine);
    }
}
