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
public class DefaultParser_ESTest_test18 extends DefaultParser_ESTest_scaffolding {

    /**
     * Verifies that parsing an argument array where a required option flag is present
     * but its mandatory argument value is absent (remaining slots are null) throws
     * a MissingArgumentException, even when stopAtNonOption is true.
     */
    @Test(timeout = 4000)
    public void test18() throws Throwable {
        // Define a required option "-s4" that expects one argument value
        Options options = new Options();
        Options optionsWithRequiredS4 = options.addRequiredOption("s4", "s4", true, "-s4");

        DefaultParser parser = new DefaultParser();

        // The array has the option flag at index 0 but no argument value follows (indices 1 and 2 are null)
        String[] argsWithMissingValue = new String[3];
        argsWithMissingValue[0] = "-s4";

        try {
            parser.parse(optionsWithRequiredS4, argsWithMissingValue, true);
            fail("Expecting exception: Exception");
        } catch (Exception e) {
            //
            // Missing argument for option: s4
            //
            verifyException("org.apache.commons.cli.DefaultParser", e);
        }
    }
}
