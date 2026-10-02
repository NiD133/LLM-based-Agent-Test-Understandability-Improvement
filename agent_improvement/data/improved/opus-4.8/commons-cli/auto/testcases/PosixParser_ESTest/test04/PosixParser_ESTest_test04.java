package org.apache.commons.cli;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class PosixParser_ESTest_test04 extends PosixParser_ESTest_scaffolding {

    /**
     * Parsing arguments that contain a {@code null} entry causes the PosixParser
     * to throw a NullPointerException while flattening the argument list.
     */
    @Test(timeout = 4000)
    public void test04() throws Throwable {
        // Arguments array whose entries are mostly null (only index 17 is set).
        String[] arguments = new String[38];
        arguments[17] = "---";

        // Register an option so that flattening has something to match against.
        // The short option name and description are intentionally null.
        Option option = new Option(null, "---", false, null);
        Options options = new Options().addOption(option);

        PosixParser parser = new PosixParser();
        try {
            parser.parse(options, arguments);
            fail("Expecting exception: NullPointerException");
        } catch (NullPointerException e) {
            // The null argument entries trigger an NPE inside PosixParser with no message.
            verifyException("org.apache.commons.cli.PosixParser", e);
        }
    }
}
