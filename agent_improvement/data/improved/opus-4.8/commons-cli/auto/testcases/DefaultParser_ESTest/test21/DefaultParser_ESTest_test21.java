package org.apache.commons.cli;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class DefaultParser_ESTest_test21 extends DefaultParser_ESTest_scaffolding {

    /**
     * Parsing an argument that starts with a hyphen but matches no defined option
     * must fail. With the default non-option handling (THROW), the parser reports the
     * token as an unrecognized option instead of treating it as a plain argument.
     */
    @Test(timeout = 4000)
    public void parsingUnrecognizedDashOptionThrowsException() throws Throwable {
        // Define a single required option; it is never supplied on the command line.
        Options options = new Options();
        options.addRequiredOption("s4", "s4", false, "--s4");

        DefaultParser parser = new DefaultParser();

        // Build the command-line arguments: only the last slot holds a value, an
        // option-like token ("-=};SP'") that does not correspond to any defined option.
        String[] arguments = new String[8];
        arguments[7] = "-=};SP'";

        try {
            // stopAtNonOption = false => unrecognized dash-prefixed tokens trigger an exception.
            parser.parse(options, arguments, false);
            fail("Expecting exception: Exception");
        } catch (Exception e) {
            // Unrecognized option: -=};SP'
            verifyException("org.apache.commons.cli.DefaultParser", e);
        }
    }
}
