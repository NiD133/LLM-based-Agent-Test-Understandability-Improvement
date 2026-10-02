package org.apache.commons.cli;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class PosixParser_ESTest_test07 extends PosixParser_ESTest_scaffolding {

    /**
     * Verifies that PosixParser can parse an argument array containing mostly null entries
     * alongside a standalone dash ("-"). The flatten() method skips null tokens, and a bare
     * "-" is treated as a non-option token rather than an option flag.
     */
    @Test(timeout = 4000)
    public void test_parseWithSingleDashAmongNullArguments_returnsCommandLine() throws Throwable {
        // 65-element array; all entries default to null except index 4 which holds "-"
        String[] args = new String[65];
        args[4] = "-";

        Options options = new Options();
        PosixParser posixParser = new PosixParser();

        CommandLine commandLine = posixParser.parse(options, args);

        assertNotNull(commandLine);
    }
}
