package org.apache.commons.cli;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CommandLine_ESTest_test41 extends CommandLine_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void getParsedOptionValues_returnsNull_whenOptionNotPresent() throws Throwable {
        CommandLine commandLine = new CommandLine();
        Option[] parsedValues = commandLine.getParsedOptionValues('W');
        assertNull(parsedValues);
    }
}
