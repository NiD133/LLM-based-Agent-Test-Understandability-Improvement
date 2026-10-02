package org.apache.commons.cli;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CommandLine_ESTest_test33 extends CommandLine_ESTest_scaffolding {

    /**
     * An empty CommandLine has no parsed options, so querying the values of any
     * option (here the unparsed short option '7') should return null.
     */
    @Test(timeout = 4000)
    public void getOptionValuesForUnknownCharOptionReturnsNull() throws Throwable {
        CommandLine emptyCommandLine = new CommandLine();

        String[] valuesForUnknownOption = emptyCommandLine.getOptionValues('7');

        assertNull(valuesForUnknownOption);
    }
}
