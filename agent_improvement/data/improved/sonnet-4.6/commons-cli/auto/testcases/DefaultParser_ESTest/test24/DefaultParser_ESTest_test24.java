package org.apache.commons.cli;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class DefaultParser_ESTest_test24 extends DefaultParser_ESTest_scaffolding {

    /**
     * Verifies that parsing an unrecognized triple-dashed option (e.g. "---fo=...")
     * with stopAtNonOption=false throws an UnrecognizedOptionException.
     * The args array has 9 slots; only index 1 holds the offending token —
     * null slots are skipped by the parser.
     */
    @Test(timeout = 4000)
    public void test24() throws Throwable {
        // Disable partial matching so no prefix guessing occurs
        DefaultParser parser = new DefaultParser(false);
        Options options = new Options();

        // Place the unrecognized triple-dashed token at index 1; remaining slots are null
        String[] args = new String[9];
        args[1] = "---fo=9EbdgIv{'d^Zj";

        try {
            // stopAtNonOption=false: unrecognized options must throw rather than be silently skipped
            parser.parse(options, args, false);
            fail("Expecting exception: Exception");
        } catch (Exception e) {
            //
            // Unrecognized option: ---fo=9EbdgIv{'d^Zj
            //
            verifyException("org.apache.commons.cli.DefaultParser", e);
        }
    }
}
