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
     * Verifies that parsing an argument array where the first element is a concatenated
     * short-option-plus-value token ("-s#") succeeds when "-s" is a known option that
     * accepts an argument. The remaining nine array slots are null and must be tolerated
     * without causing a NullPointerException or ParseException.
     */
    @Test(timeout = 4000)
    public void test25() throws Throwable {
        // Register option "-s" as one that accepts an argument value
        Options options = new Options();
        options.addOption("s", true, "s");

        // Build an args array where the first token combines the option flag and its value
        // ("-s#" means: option "s" with value "#"), followed by nine null slots
        String[] args = new String[10];
        args[0] = "-s#";

        DefaultParser parser = new DefaultParser();
        CommandLine commandLine = parser.parse(options, args, false);

        assertNotNull(commandLine);
    }
}
