package org.apache.commons.cli;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Properties;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CommandLine_ESTest_test20 extends CommandLine_ESTest_scaffolding {

    /**
     * An option that has no parsed argument values should yield an empty
     * Properties map from {@link CommandLine#getOptionProperties(String)},
     * even when the requested name matches the option.
     */
    @Test(timeout = 4000)
    public void getOptionProperties_withoutValues_returnsEmptyProperties() throws Throwable {
        // Given a command line containing an option that has no argument values
        Option optionWithoutValues = new Option("ClS", "", true, "");
        CommandLine commandLine = new CommandLine();
        commandLine.addOption(optionWithoutValues);

        // When retrieving the option's properties
        Properties optionProperties = commandLine.getOptionProperties("");

        // Then the resulting Properties map is empty
        assertTrue(optionProperties.isEmpty());
    }
}
