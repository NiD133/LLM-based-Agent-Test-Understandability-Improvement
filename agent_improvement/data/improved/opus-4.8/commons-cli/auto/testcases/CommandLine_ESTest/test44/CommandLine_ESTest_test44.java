package org.apache.commons.cli;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CommandLine_ESTest_test44 extends CommandLine_ESTest_scaffolding {

    /**
     * When the requested option is absent from the command line,
     * {@link CommandLine#getOptionValue(char, String)} should return the supplied
     * default value unchanged.
     */
    @Test(timeout = 4000)
    public void getOptionValueReturnsDefaultWhenOptionAbsent() throws Throwable {
        CommandLine emptyCommandLine = new CommandLine();
        String defaultValue = "]|BUYd1uG";

        String result = emptyCommandLine.getOptionValue('C', defaultValue);

        assertEquals(defaultValue, result);
    }
}
