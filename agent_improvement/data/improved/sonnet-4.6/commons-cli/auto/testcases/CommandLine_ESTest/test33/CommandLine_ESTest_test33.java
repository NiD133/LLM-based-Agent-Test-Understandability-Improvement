package org.apache.commons.cli;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CommandLine_ESTest_test33 extends CommandLine_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test_getOptionValues_returnsNull_whenOptionNotPresent() throws Throwable {
        // A freshly constructed CommandLine has no options, so querying for any
        // option's values must return null.
        CommandLine commandLine = new CommandLine();
        String[] values = commandLine.getOptionValues('7');
        assertNull(values);
    }
}
