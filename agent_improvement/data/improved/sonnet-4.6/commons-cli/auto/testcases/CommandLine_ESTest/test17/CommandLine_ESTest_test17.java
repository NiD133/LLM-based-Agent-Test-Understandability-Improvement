package org.apache.commons.cli;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CommandLine_ESTest_test17 extends CommandLine_ESTest_scaffolding {

    /**
     * When the command line contains an option with no value, getOptionValue should
     * return the provided default value instead of null.
     */
    @Test(timeout = 4000)
    public void test_getOptionValue_returnsDefaultWhenOptionHasNoValue() throws Throwable {
        // Set up a command line with a no-value option "-GPmL"
        CommandLine commandLine = new CommandLine();
        Option optionWithNoValue = new Option("GPmL", "GPmL");
        commandLine.addOption(optionWithNoValue);

        // Build a matching option with the same short and long opt names
        Option queryOption = Option.builder("GPmL").longOpt("GPmL").get();

        // Since queryOption matches optionWithNoValue but carries no value,
        // getOptionValue should fall back to the supplied default
        String result = commandLine.getOptionValue(queryOption, "GPmL");
        assertEquals("GPmL", result);
    }
}
