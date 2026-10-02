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
     * Verifies that parsing throws MissingArgumentException when a short option
     * that requires an argument is given twice with no actual argument value.
     *
     * Setup: option "s" (short, hasArg=true) and a long option "s" are registered.
     * Input "-s -s" causes the second "-s" to be treated as a new option invocation
     * rather than the argument of the first, leaving the first "-s" without its
     * required argument.
     */
    @Test(timeout = 4000)
    public void test_parseThrowsMissingArgumentExceptionWhenShortOptionLacksArgument() throws Throwable {
        DefaultParser parser = new DefaultParser();

        Options options = new Options();

        // Long option named "s" (no short name, no argument)
        Option.Builder longOptBuilder = Option.builder();
        longOptBuilder.longOpt("s");
        Option longOptionS = longOptBuilder.get();
        options.addOption(longOptionS);

        // Short option "s" that requires an argument
        options.addOption("s", true, "-s");

        // Only the first two slots are set; the rest remain null
        String[] args = new String[7];
        args[0] = "-s";
        args[1] = "-s";

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
