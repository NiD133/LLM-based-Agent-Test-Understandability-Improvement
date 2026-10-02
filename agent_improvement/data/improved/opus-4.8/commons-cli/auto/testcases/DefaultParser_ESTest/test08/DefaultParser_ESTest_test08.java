package org.apache.commons.cli;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class DefaultParser_ESTest_test08 extends DefaultParser_ESTest_scaffolding {

    /**
     * Verifies that parsing fails when an option that requires an argument is
     * immediately followed by another occurrence of the same option instead of
     * its expected argument value.
     *
     * <p>The option "s" is defined as requiring an argument. The command line
     * "-s -s" supplies "-s" where the argument for the first "-s" should be, so
     * the parser reports a missing argument for option "s".</p>
     */
    @Test(timeout = 4000)
    public void parseOptionRequiringArgFollowedByOptionThrowsMissingArgument() throws Throwable {
        DefaultParser parser = new DefaultParser();

        Options options = new Options();
        options.addOption("s", true, "-s");

        // "-s" is given where the argument for the first "-s" is expected.
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
