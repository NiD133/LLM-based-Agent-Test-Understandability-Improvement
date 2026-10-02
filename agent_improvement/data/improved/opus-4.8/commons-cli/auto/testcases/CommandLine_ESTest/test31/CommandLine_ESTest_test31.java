package org.apache.commons.cli;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CommandLine_ESTest_test31 extends CommandLine_ESTest_scaffolding {

    /**
     * When an empty {@link OptionGroup} has no option selected, parsing its value
     * returns the supplied default. Here the default is a {@code null} Option, so
     * the result is {@code null}.
     */
    @Test(timeout = 4000)
    public void parsedOptionValueOfUnselectedGroupReturnsDefault() throws Throwable {
        CommandLine commandLine = new CommandLine();
        OptionGroup unselectedGroup = new OptionGroup();
        Option defaultOption = null;

        Option parsedValue = commandLine.getParsedOptionValue(unselectedGroup, defaultOption);

        assertNull(parsedValue);
    }
}
