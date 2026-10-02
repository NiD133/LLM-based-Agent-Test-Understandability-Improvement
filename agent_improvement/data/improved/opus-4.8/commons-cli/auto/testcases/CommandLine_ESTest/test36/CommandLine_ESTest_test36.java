package org.apache.commons.cli;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CommandLine_ESTest_test36 extends CommandLine_ESTest_scaffolding {

    /**
     * When an option group is not selected, {@link CommandLine#getOptionValue(OptionGroup, String)}
     * should fall back to the supplied default value.
     */
    @Test(timeout = 4000)
    public void getOptionValueForUnselectedGroupReturnsDefaultValue() throws Throwable {
        CommandLine commandLine = new CommandLine();
        OptionGroup unselectedGroup = new OptionGroup();
        String defaultValue = "u\" G.tb";

        String result = commandLine.getOptionValue(unselectedGroup, defaultValue);

        assertEquals(defaultValue, result);
    }
}
