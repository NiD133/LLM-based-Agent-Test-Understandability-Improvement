package org.apache.commons.cli;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CommandLine_ESTest_test14 extends CommandLine_ESTest_scaffolding {

    /**
     * Verifies that parsing the value of a null OptionGroup yields null,
     * since a null group is treated as "not selected".
     */
    @Test(timeout = 4000)
    public void getParsedOptionValue_withNullOptionGroup_returnsNull() throws Throwable {
        CommandLine commandLine = new CommandLine();

        Option parsedValue = commandLine.getParsedOptionValue((OptionGroup) null);

        assertNull(parsedValue);
    }
}
