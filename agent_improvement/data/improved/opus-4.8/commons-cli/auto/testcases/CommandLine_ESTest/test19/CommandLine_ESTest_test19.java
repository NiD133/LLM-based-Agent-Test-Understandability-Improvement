package org.apache.commons.cli;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Properties;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CommandLine_ESTest_test19 extends CommandLine_ESTest_scaffolding {

    /**
     * Verifies that {@link CommandLine#getOptionProperties(String)} returns an empty
     * {@link Properties} object when no processed option matches the requested name.
     */
    @Test(timeout = 4000)
    public void getOptionProperties_returnsEmpty_whenOptionNameDoesNotMatch() throws Throwable {
        CommandLine commandLine = new CommandLine();
        Option option = new Option("1", "1");
        commandLine.addOption(option);

        // ":nx1" matches neither the short nor the long name of the added option.
        Properties properties = commandLine.getOptionProperties(":nx1");

        assertTrue(properties.isEmpty());
    }
}
