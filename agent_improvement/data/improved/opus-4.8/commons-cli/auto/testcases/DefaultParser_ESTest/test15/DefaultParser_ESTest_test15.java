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
     * Parsing a token of the form "-c=wt9" against an option named "c" that takes
     * no argument must fail: because the option does not accept a value, the whole
     * token is treated as unrecognized and an UnrecognizedOptionException is thrown.
     */
    @Test(timeout = 4000)
    public void parsingValueForArgumentlessOptionThrowsUnrecognizedOption() throws Throwable {
        // An option with short name "c" that accepts no argument (hasArg = false).
        Options options = new Options();
        options.addRequiredOption("c", "-c=wt9", false, "-c");

        DefaultParser parser = new DefaultParser(false);

        // First argument supplies a value to the argument-less option; the second is unused.
        String[] arguments = new String[2];
        arguments[0] = "-c=wt9";

        try {
            parser.parse(options, arguments, false);
            fail("Expecting exception: Exception");
        } catch (Exception e) {
            // Unrecognized option: -c=wt9
            verifyException("org.apache.commons.cli.DefaultParser", e);
        }
    }
}
