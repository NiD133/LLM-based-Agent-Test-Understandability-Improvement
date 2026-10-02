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
public class DefaultParser_ESTest_test20 extends DefaultParser_ESTest_scaffolding {

    /**
     * Verifies that a required option defined with long name "s4" can be satisfied by the
     * argument "--s" via DefaultParser's partial long-option matching. The args array contains
     * two leading null entries (which are silently skipped) and then "--s", which partially
     * matches "--s4". Parsing must succeed and return a non-null CommandLine.
     */
    @Test(timeout = 4000)
    public void test20() throws Throwable {
        // Register "s4" as a required flag option (no argument, short opt "s4", long opt "s4")
        Options options = new Options();
        Options optionsWithRequired = options.addRequiredOption("s4", "s4", false, "--s");

        // "--s" partially matches the long option "--s4"; the two null entries are skipped
        String[] args = new String[3];
        args[2] = "--s";

        DefaultParser parser = new DefaultParser();
        // stopAtNonOption=false: unrecognized tokens would throw, but "--s" is a valid partial match
        CommandLine result = parser.parse(optionsWithRequired, args, false);

        assertNotNull(result);
    }
}
