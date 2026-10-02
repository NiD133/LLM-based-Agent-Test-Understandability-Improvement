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
     * Verifies that when option "-s" (which requires an argument) is immediately followed
     * by another option flag "-s" instead of a value, the parser throws a MissingArgumentException.
     * The stopAtNonOption flag is set to true, but since the missing argument is detected
     * before any non-option token, parsing still aborts with an exception.
     */
    @Test(timeout = 4000)
    public void test08() throws Throwable {
        DefaultParser parser = new DefaultParser();

        Options options = new Options();
        options.addOption("s", true, "-s"); // "s" is a short option that requires an argument

        // args[0] = "-s" sets currentOption to "s" (awaiting its required argument)
        // args[1] = "-s" is another option flag, not a value — triggers MissingArgumentException
        // args[2..6] are null and are ignored by the parser
        String[] args = new String[7];
        args[0] = "-s";
        args[1] = "-s";

        try {
            parser.parse(options, args, true);
            fail("Expecting exception: Exception");
        } catch (Exception e) {
            verifyException("org.apache.commons.cli.DefaultParser", e);
        }
    }
}
