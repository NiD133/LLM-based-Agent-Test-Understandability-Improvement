package org.apache.commons.cli;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class DefaultParser_ESTest_test09 extends DefaultParser_ESTest_scaffolding {

    /**
     * When an option that requires an argument ("-s") is itself given as the
     * argument of a preceding "-s", the parser runs out of values for the second
     * "-s" and reports a missing argument.
     */
    @Test(timeout = 4000)
    public void parseFailsWhenArgumentRequiringOptionIsMissingItsValue() throws Throwable {
        DefaultParser parser = new DefaultParser();

        // Register option "s" twice: once with only the long name "s", and once
        // as a short option "s" that requires an argument.
        Options options = new Options();
        Option longOnlyOption = Option.builder().longOpt("s").get();
        options.addOption(longOnlyOption);
        options.addOption("s", true, "-s");

        // First "-s" claims the second "-s" as its (only) value, leaving the
        // second "-s" without any argument of its own.
        String[] arguments = new String[7];
        arguments[0] = "-s";
        arguments[1] = "-s";

        try {
            parser.parse(options, arguments, true);
            fail("Expecting exception: Exception");
        } catch (Exception e) {
            // Missing argument for option: s
            verifyException("org.apache.commons.cli.DefaultParser", e);
        }
    }
}
