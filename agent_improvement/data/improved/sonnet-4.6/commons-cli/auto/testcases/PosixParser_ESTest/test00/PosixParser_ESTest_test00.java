package org.apache.commons.cli;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class PosixParser_ESTest_test00 extends PosixParser_ESTest_scaffolding {

    /**
     * Verifies that parsing an unrecognized short option ("-D") with stopAtNonOption=true
     * against an empty Options set returns a valid (non-null) CommandLine object.
     * The second argument slot is left null, which is valid input.
     */
    @Test(timeout = 4000)
    public void test_parseUnrecognizedShortOption_withStopAtNonOption_returnsCommandLine() throws Throwable {
        PosixParser parser = new PosixParser();
        Options emptyOptions = new Options();

        String[] args = new String[2];
        args[0] = "-D";
        // args[1] is intentionally null — the parser must handle sparse argument arrays

        CommandLine result = parser.parse(emptyOptions, args, true);

        assertNotNull(result);
    }
}
