package org.apache.commons.cli;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CommandLine_ESTest_test25 extends CommandLine_ESTest_scaffolding {

    /**
     * Verifies that addArg() appends the given argument string to the CommandLine's
     * unrecognized-arguments list without throwing an exception.
     */
    @Test(timeout = 4000)
    public void testAddArgAppendsStringToArgList() throws Throwable {
        CommandLine commandLine0 = new CommandLine();
        commandLine0.addArg("true");
        assertTrue(commandLine0.getArgList().contains("true"));
    }
}
