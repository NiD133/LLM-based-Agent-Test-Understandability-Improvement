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
     * Verifies that a short option with a concatenated value (e.g. "-s#") is parsed
     * successfully when the option is registered as accepting an argument.
     * The remaining null slots in the args array are ignored by the parser.
     * stopAtNonOption=false means unrecognized tokens would throw, but here
     * "#" is consumed as the value for "-s", so no exception is expected.
     */
    @Test(timeout = 4000)
    public void test25() throws Throwable {
        // Register option "s" that accepts one argument
        Options options = new Options();
        options.addOption("s", true, "s");

        // Build an args array where only the first slot is set; the rest are null
        String[] args = new String[10];
        args[0] = "-s#"; // short option "-s" with concatenated value "#"

        DefaultParser parser = new DefaultParser();

        // Parse with stopAtNonOption=false: unrecognized tokens would throw,
        // but "-s#" is fully recognized so the result must be non-null
        CommandLine commandLine = parser.parse(options, args, false);

        assertNotNull(commandLine);
    }
}
