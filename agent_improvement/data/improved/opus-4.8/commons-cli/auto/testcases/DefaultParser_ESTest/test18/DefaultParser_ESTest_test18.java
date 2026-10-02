package org.apache.commons.cli;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class DefaultParser_ESTest_test18 extends DefaultParser_ESTest_scaffolding {

    /**
     * Parsing a required option that expects an argument must fail when the
     * option appears on the command line but its argument value is missing.
     * Here "-s4" is supplied with no following value, so the parser is
     * expected to throw a MissingArgumentException ("Missing argument for
     * option: s4").
     */
    @Test(timeout = 4000)
    public void parsingRequiredOptionWithoutItsArgumentThrowsMissingArgument() throws Throwable {
        // Define a required option "s4" that requires an argument value.
        Options options = new Options();
        options.addRequiredOption("s4", "s4", true, "-s4");

        DefaultParser parser = new DefaultParser();

        // Provide the option token but omit its argument value.
        String[] arguments = new String[3];
        arguments[0] = "-s4";

        try {
            parser.parse(options, arguments, true);
            fail("Expecting exception: Exception");
        } catch (Exception e) {
            // Missing argument for option: s4
            verifyException("org.apache.commons.cli.DefaultParser", e);
        }
    }
}
