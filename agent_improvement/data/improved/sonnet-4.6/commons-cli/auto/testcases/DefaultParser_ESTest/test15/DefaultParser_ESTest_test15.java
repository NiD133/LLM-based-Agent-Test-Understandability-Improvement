package org.apache.commons.cli;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class DefaultParser_ESTest_test15 extends DefaultParser_ESTest_scaffolding {

    /**
     * Passing "-c=wt9" should throw UnrecognizedOptionException because the registered
     * short option "c" does not accept arguments (hasArg=false), so the "-c=VALUE" form
     * is unrecognized when partial matching is disabled and stopAtNonOption is false.
     */
    @Test(timeout = 4000)
    public void test15() throws Throwable {
        Options options = new Options();
        // Register short option "c" with long name "-c=wt9", no argument, not stopping at non-option
        Options optionsWithRequiredC = options.addRequiredOption("c", "-c=wt9", false, "-c");

        DefaultParser parser = new DefaultParser(false);

        // Argument "-c=wt9" uses the "short option with value" form, but option "c" takes no arg
        String[] args = new String[2];
        args[0] = "-c=wt9";

        try {
            parser.parse(optionsWithRequiredC, args, false);
            fail("Expecting exception: Exception");
        } catch (Exception e) {
            verifyException("org.apache.commons.cli.DefaultParser", e);
        }
    }
}
