package org.apache.commons.cli;

import static org.junit.Assert.assertTrue;

import java.util.Properties;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CommandLine_ESTest_test22 extends CommandLine_ESTest_scaffolding {

    /**
     * Verifies that requesting option properties for a {@code null} option returns an empty
     * {@link Properties}: none of the registered options can equal {@code null}, so no values
     * are collected.
     */
    @Test(timeout = 4000)
    public void getOptionPropertiesForNullOptionReturnsEmptyProperties() throws Throwable {
        Option registeredOption = new Option("L", "L");
        CommandLine commandLine = new CommandLine();
        commandLine.addOption(registeredOption);

        Properties properties = commandLine.getOptionProperties((Option) null);

        assertTrue(properties.isEmpty());
    }
}
