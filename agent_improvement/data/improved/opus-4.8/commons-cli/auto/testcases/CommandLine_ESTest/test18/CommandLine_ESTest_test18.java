package org.apache.commons.cli;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CommandLine_ESTest_test18 extends CommandLine_ESTest_scaffolding {

    /**
     * When an option holds a parsed value, {@code getOptionValue(name, defaultValue)}
     * returns that stored value rather than the supplied default.
     */
    @Test(timeout = 4000)
    public void getOptionValueReturnsStoredValueInsteadOfDefault() throws Throwable {
        // Build an option that takes an argument, named "options" (both short and long name).
        Option option = new Option("options", "options", true, "options");

        // Give the option a parsed value, then register it on the command line.
        option.processValue("options");
        CommandLine commandLine = new CommandLine();
        commandLine.addOption(option);

        // The stored value "options" should be returned, not the default "options".
        String value = commandLine.getOptionValue("options", "options");

        assertEquals("options", value);
    }
}
