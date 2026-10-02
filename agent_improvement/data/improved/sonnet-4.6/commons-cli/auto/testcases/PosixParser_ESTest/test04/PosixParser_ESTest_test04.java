package org.apache.commons.cli;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class PosixParser_ESTest_test04 extends PosixParser_ESTest_scaffolding {

    /**
     * Verifies that parsing throws NullPointerException when an Option is
     * constructed with a null opt key (args[27] is uninitialized, so null)
     * and the argument array contains "---" at index 17.
     *
     * The "---" token is treated as a long option by PosixParser; looking up
     * the registered option by its null key triggers an NPE inside PosixParser.
     */
    @Test(timeout = 4000)
    public void test04() throws Throwable {
        // 38-element array; all slots default to null except index 17
        String[] args = new String[38];
        args[17] = "---";

        // Option with null opt (args[27]) and long-opt "---"; description also null (args[1])
        Options options = new Options();
        Option optionWithNullKey = new Option(args[27], "---", false, args[1]);
        Options configuredOptions = options.addOption(optionWithNullKey);

        // Parsing an array that contains "---" with a null-keyed option must throw NPE
        PosixParser parser = new PosixParser();
        try {
            parser.parse(configuredOptions, args);
            fail("Expecting exception: NullPointerException");
        } catch (NullPointerException e) {
            verifyException("org.apache.commons.cli.PosixParser", e);
        }
    }
}
