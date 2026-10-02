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
     * Verifies that parsing an argument array containing an unrecognized option token
     * throws an exception when stopAtNonOption is false.
     *
     * The argument array has 9 slots; index 2 holds "-s#" (an option-like token with
     * no matching option registered), while all other slots remain null.
     * With stopAtNonOption=false the parser must throw instead of silently skipping
     * the unknown token.
     */
    @Test(timeout = 4000)
    public void test27() throws Throwable {
        Options emptyOptions = new Options();
        DefaultParser parser = new DefaultParser();

        // Build an argument array that places the unrecognized option at index 2;
        // the surrounding null slots are part of the original array shape.
        String[] args = new String[9];
        args[2] = "-s#";

        try {
            parser.parse(emptyOptions, args, false);
            fail("Expecting exception: Exception");
        } catch (Exception e) {
            // Expected: unrecognized option "-s#" must raise an exception
            // when the parser is not configured to stop-at-non-option.
            verifyException("org.apache.commons.cli.DefaultParser", e);
        }
    }
}
