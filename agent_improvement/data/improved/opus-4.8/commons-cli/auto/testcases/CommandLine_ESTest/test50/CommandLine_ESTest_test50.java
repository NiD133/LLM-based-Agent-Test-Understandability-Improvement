package org.apache.commons.cli;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CommandLine_ESTest_test50 extends CommandLine_ESTest_scaffolding {

    /**
     * Querying an option on an empty command line should return {@code null},
     * because no options were ever added to the builder.
     */
    @Test(timeout = 4000)
    public void getOptionValueOnEmptyCommandLineReturnsNull() throws Throwable {
        CommandLine emptyCommandLine = CommandLine.builder().get();

        String optionValue = emptyCommandLine.getOptionValue('f');

        assertNull(optionValue);
    }
}
