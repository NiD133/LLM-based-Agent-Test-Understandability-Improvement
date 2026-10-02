package org.apache.commons.cli;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CommandLine_ESTest_test48 extends CommandLine_ESTest_scaffolding {

    /**
     * Verifies that getParsedOptionValue returns null when the requested option
     * was never added to the CommandLine (i.e., option 'f' is absent).
     */
    @Test(timeout = 4000)
    public void test48() throws Throwable {
        CommandLine commandLine = new CommandLine();
        Object parsedValue = commandLine.getParsedOptionValue('f');
        assertNull(parsedValue);
    }
}
