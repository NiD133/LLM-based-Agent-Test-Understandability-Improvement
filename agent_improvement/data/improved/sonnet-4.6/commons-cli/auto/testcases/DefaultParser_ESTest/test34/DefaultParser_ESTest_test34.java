package org.apache.commons.cli;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class DefaultParser_ESTest_test34 extends DefaultParser_ESTest_scaffolding {

    /**
     * Verifies that parsing an option that requires an argument ("-s") when the next
     * token is null (missing argument) and a subsequent option token ("-s#") follows,
     * causes a MissingArgumentException for the incomplete first option.
     *
     * The argument array has 7 slots: index 0 is "-s", index 1 is null (no argument
     * provided for -s), and index 2 is "-s#" (another option, which triggers processing
     * of the still-pending first -s and exposes the missing argument).
     */
    @Test(timeout = 4000)
    public void test34() throws Throwable {
        Options options = new Options();
        options.addOption("s", true, "s");

        DefaultParser parser = new DefaultParser();

        // 7-element array: "-s" at [0], null at [1] (missing argument), "-s#" at [2]
        String[] args = new String[7];
        args[0] = "-s";
        args[2] = "-s#";

        try {
            parser.parse(options, args, true);
            fail("Expecting exception: Exception");
        } catch (Exception e) {
            //
            // Missing argument for option: s
            //
            verifyException("org.apache.commons.cli.DefaultParser", e);
        }
    }
}
