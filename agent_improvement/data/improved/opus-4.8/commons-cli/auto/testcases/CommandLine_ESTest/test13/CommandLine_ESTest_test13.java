package org.apache.commons.cli;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CommandLine_ESTest_test13 extends CommandLine_ESTest_scaffolding {

    /**
     * Requesting parsed values for a {@code null} OptionGroup should yield {@code null},
     * since there is no selected option to convert.
     */
    @Test(timeout = 4000)
    public void getParsedOptionValuesForNullOptionGroupReturnsNull() throws Throwable {
        CommandLine commandLine = CommandLine.builder().get();

        Option[] parsedValues = commandLine.getParsedOptionValues((OptionGroup) null);

        assertNull(parsedValues);
    }
}
