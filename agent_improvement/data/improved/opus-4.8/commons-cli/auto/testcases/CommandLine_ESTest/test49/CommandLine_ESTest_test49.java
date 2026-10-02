package org.apache.commons.cli;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CommandLine_ESTest_test49 extends CommandLine_ESTest_scaffolding {

    /**
     * When an option is not present on the command line, {@code getParsedOptionValues}
     * returns the supplied default value. Here the default is an explicit {@code null}
     * array, so the method is expected to return {@code null}.
     */
    @Test(timeout = 4000)
    public void getParsedOptionValuesForMissingOptionReturnsNullDefault() throws Throwable {
        CommandLine emptyCommandLine = new CommandLine();
        Class<Option>[] nullDefault = null;

        Class<Option>[] parsedValues = emptyCommandLine.getParsedOptionValues('_', nullDefault);

        assertNull(parsedValues);
    }
}
