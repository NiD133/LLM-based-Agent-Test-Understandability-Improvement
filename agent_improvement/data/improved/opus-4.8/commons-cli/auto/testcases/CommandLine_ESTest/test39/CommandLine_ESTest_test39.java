package org.apache.commons.cli;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CommandLine_ESTest_test39 extends CommandLine_ESTest_scaffolding {

    /**
     * When an option group has no option selected, parsing its values should fall back
     * to the provided default value. Here the default value is {@code null}, so the
     * result is expected to be {@code null}.
     */
    @Test(timeout = 4000)
    public void parsedOptionValuesOfUnselectedGroupReturnsNullDefault() throws Throwable {
        CommandLine commandLine = CommandLine.builder().get();
        OptionGroup unselectedGroup = new OptionGroup();

        Class<Option>[] defaultValue = null;
        Class<Option>[] parsedValues = commandLine.getParsedOptionValues(unselectedGroup, defaultValue);

        assertNull(parsedValues);
    }
}
