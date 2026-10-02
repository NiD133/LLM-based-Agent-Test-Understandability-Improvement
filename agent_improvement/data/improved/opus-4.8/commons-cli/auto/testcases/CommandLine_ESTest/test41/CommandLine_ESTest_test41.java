package org.apache.commons.cli;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CommandLine_ESTest_test41 extends CommandLine_ESTest_scaffolding {

    /**
     * An empty CommandLine has no parsed options, so requesting the parsed values
     * for any option character returns null.
     */
    @Test(timeout = 4000)
    public void getParsedOptionValuesForUnknownOptionReturnsNull() throws Throwable {
        CommandLine emptyCommandLine = new CommandLine();

        Option[] parsedValues = emptyCommandLine.getParsedOptionValues('W');

        assertNull(parsedValues);
    }
}
