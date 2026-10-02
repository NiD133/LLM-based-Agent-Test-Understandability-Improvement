package org.apache.commons.cli;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class DefaultParser_ESTest_test27 extends DefaultParser_ESTest_scaffolding {

    /**
     * Parsing an argument that looks like a short option ("-s#") but is not
     * declared in the (empty) Options set should fail. Because stopAtNonOption
     * is false, the parser throws an UnrecognizedOptionException instead of
     * treating the token as a plain argument.
     */
    @Test(timeout = 4000)
    public void parseUnrecognizedShortOptionThrows() throws Throwable {
        Options noOptions = new Options();
        DefaultParser parser = new DefaultParser();

        // Only the third token is an unrecognized option; the rest are null.
        String[] arguments = new String[9];
        arguments[2] = "-s#";

        boolean stopAtNonOption = false;
        try {
            parser.parse(noOptions, arguments, stopAtNonOption);
            fail("Expecting exception: Exception");
        } catch (Exception e) {
            // Unrecognized option: -s#
            verifyException("org.apache.commons.cli.DefaultParser", e);
        }
    }
}
