package org.apache.commons.cli;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CommandLine_ESTest_test07 extends CommandLine_ESTest_scaffolding {

    /**
     * Verifies that {@link CommandLine#hasOption(OptionGroup)} returns {@code false}
     * when the group's selected option was never added to the command line.
     */
    @Test(timeout = 4000)
    public void hasOptionForUnaddedSelectedGroupOptionReturnsFalse() throws Throwable {
        CommandLine emptyCommandLine = new CommandLine();

        // Build an option and mark it as the selected member of a group,
        // but never add it to the command line.
        Option selectedOption = new Option("K", "_", true, "_");
        OptionGroup optionGroup = new OptionGroup();
        optionGroup.setSelected(selectedOption);

        boolean hasOption = emptyCommandLine.hasOption(optionGroup);

        assertFalse(hasOption);
    }
}
