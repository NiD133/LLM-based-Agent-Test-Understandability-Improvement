package org.apache.commons.cli;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CommandLine_ESTest_test24 extends CommandLine_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void testAddNullOptionIsIgnoredSilently() throws Throwable {
        CommandLine commandLine = new CommandLine();

        // addOption should silently ignore a null argument rather than throwing
        commandLine.addOption((Option) null);

        // No option should have been added to the command line
        assertEquals(0, commandLine.getOptions().length);
    }
}
