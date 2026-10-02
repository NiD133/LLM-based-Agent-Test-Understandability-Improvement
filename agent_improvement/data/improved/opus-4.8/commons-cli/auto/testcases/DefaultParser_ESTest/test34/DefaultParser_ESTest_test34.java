package org.apache.commons.cli;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class DefaultParser_ESTest_test34 extends DefaultParser_ESTest_scaffolding {

    /**
     * Verifies that parsing fails when an option that requires an argument is
     * immediately followed by another option-like token instead of its value.
     *
     * Here option "-s" requires an argument, but the next meaningful token is
     * "-s#" (which the parser treats as an option, not as the missing value).
     * The parser therefore reports a missing argument for option "s".
     */
    @Test(timeout = 4000)
    public void test34() throws Throwable {
        // Define a single option "-s" that requires an argument.
        Options options = new Options();
        options.addOption("s", true, "s");

        DefaultParser parser = new DefaultParser();

        // "-s" expects a value, but the only following token "-s#" looks like
        // an option rather than a value (the unset array slots are null and ignored).
        String[] arguments = new String[7];
        arguments[0] = "-s";
        arguments[2] = "-s#";

        try {
            parser.parse(options, arguments, true);
            fail("Expecting exception: Exception");
        } catch (Exception e) {
            // Missing argument for option: s
            verifyException("org.apache.commons.cli.DefaultParser", e);
        }
    }
}
