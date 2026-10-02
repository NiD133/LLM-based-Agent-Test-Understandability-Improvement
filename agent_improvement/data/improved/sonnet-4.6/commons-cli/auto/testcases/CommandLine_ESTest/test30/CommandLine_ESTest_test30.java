package org.apache.commons.cli;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CommandLine_ESTest_test30 extends CommandLine_ESTest_scaffolding {

    /** A newly constructed CommandLine with no parsed arguments should return an empty args array. */
    @Test(timeout = 4000)
    public void test_getArgs_onEmptyCommandLine_returnsEmptyArray() throws Throwable {
        CommandLine commandLine = new CommandLine();
        String[] args = commandLine.getArgs();
        assertEquals(0, args.length);
    }
}
