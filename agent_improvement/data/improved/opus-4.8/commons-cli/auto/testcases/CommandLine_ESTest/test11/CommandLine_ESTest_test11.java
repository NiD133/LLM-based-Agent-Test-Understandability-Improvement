package org.apache.commons.cli;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CommandLine_ESTest_test11 extends CommandLine_ESTest_scaffolding {

    /**
     * Verifies that an option added to a CommandLine is subsequently reported
     * as present by hasOption(Option).
     */
    @Test(timeout = 4000)
    public void hasOptionReturnsTrueForAddedOption() throws Throwable {
        CommandLine commandLine = new CommandLine();
        Option option = new Option("GPmL", "GPmL");

        commandLine.addOption(option);

        assertTrue(commandLine.hasOption(option));
    }
}
