package org.apache.commons.cli;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.util.Properties;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class PosixParser_ESTest_test02 extends PosixParser_ESTest_scaffolding {

    /**
     * Verifies that a clustered POSIX token containing an option with an attached
     * argument is split (burst) into exactly two tokens, and that re-flattening the
     * already-split tokens leaves the token count unchanged.
     */
    @Test(timeout = 4000)
    public void test02() throws Throwable {
        PosixParser parser = new PosixParser();

        // Register a single option "-Z" that takes an argument.
        Options options = new Options();
        Option optionZ = new Option("Z", true, "");
        options.addOption(optionZ);

        // Command line containing only "-Z&=" (the remaining slots are null and ignored).
        // Here "-Z" is the option and "&=" is its attached argument value.
        String[] arguments = new String[6];
        arguments[4] = "-Z&=";

        final boolean stopAtNonOption = true;

        // First pass bursts "-Z&=" into the option "-Z" and its argument "&=".
        String[] flattenedOnce = parser.flatten(options, arguments, stopAtNonOption);

        // Second pass over the already-flattened tokens keeps them as two tokens.
        String[] flattenedTwice = parser.flatten(options, flattenedOnce, stopAtNonOption);

        assertEquals(2, flattenedTwice.length);
    }
}
