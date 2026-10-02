package org.apache.commons.cli;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CommandLine_ESTest_test08 extends CommandLine_ESTest_scaffolding {

    /**
     * An empty (unselected) OptionGroup is never considered "set" on a command line,
     * so {@link CommandLine#hasOption(OptionGroup)} must return false.
     */
    @Test(timeout = 4000)
    public void hasOptionReturnsFalseForUnselectedOptionGroup() throws Throwable {
        CommandLine commandLine = new CommandLine();
        OptionGroup unselectedGroup = new OptionGroup();

        boolean groupIsSet = commandLine.hasOption(unselectedGroup);

        assertFalse(groupIsSet);
    }
}
