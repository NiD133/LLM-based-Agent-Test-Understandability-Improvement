package org.apache.commons.cli;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CommandLine_ESTest_test26 extends CommandLine_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void testAddArgIgnoresNullInput() throws Throwable {
        CommandLine commandLine = new CommandLine();
        commandLine.addArg(null);
        assertTrue("Null argument should not be added to the arg list", commandLine.getArgList().isEmpty());
    }
}
