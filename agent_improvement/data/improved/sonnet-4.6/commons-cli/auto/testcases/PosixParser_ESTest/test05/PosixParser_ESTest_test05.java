package org.apache.commons.cli;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.util.Properties;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class PosixParser_ESTest_test05 extends PosixParser_ESTest_scaffolding {

    // Verifies that parsing a long-option token with no registered options
    // throws an "Unrecognized option" exception from the Parser layer.
    @Test(timeout = 4000)
    public void test05() throws Throwable {
        // args[0] is null (default); args[1] is a malformed long-option token "--="
        String[] args = new String[2];
        args[1] = "--=";

        Options emptyOptions = new Options();
        PosixParser posixParser0 = new PosixParser();

        try {
            posixParser0.parse(emptyOptions, args);
            fail("Expecting exception: Exception");
        } catch (Exception e) {
            // Expected: "Unrecognized option: --=" thrown by Parser
            verifyException("org.apache.commons.cli.Parser", e);
        }
    }
}
