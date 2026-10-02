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
     * Parsing an unknown short option ("-D") with no options defined and
     * stopAtNonOption enabled should still yield a non-null CommandLine.
     */
    @Test(timeout = 4000)
    public void parseUnknownShortOptionReturnsCommandLine() throws Throwable {
        PosixParser parser = new PosixParser();
        Options emptyOptions = new Options();

        // The second element is left null on purpose, matching the original scenario.
        String[] arguments = new String[2];
        arguments[0] = "-D";

        boolean stopAtNonOption = true;
        CommandLine commandLine = parser.parse(emptyOptions, arguments, stopAtNonOption);

        assertNotNull(commandLine);
    }
}
