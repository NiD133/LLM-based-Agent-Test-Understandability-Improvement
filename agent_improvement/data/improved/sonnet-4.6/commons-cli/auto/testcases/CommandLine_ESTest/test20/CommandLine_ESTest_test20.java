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
     * Verifies that getOptionProperties returns an empty Properties map when the matched option
     * has no values. The option is looked up by its long name (an empty string), and since no
     * values were ever added to it, the resulting property map must be empty.
     */
    @Test(timeout = 4000)
    public void test20() throws Throwable {
        CommandLine commandLine = new CommandLine();

        // Create an option whose long name is the empty string and that accepts an argument
        Option optionWithEmptyLongName = new Option("ClS", "", true, "");
        commandLine.addOption(optionWithEmptyLongName);

        // Look up properties via the empty-string long name; no values were set, so the map is empty
        Properties propertiesForEmptyLongName = commandLine.getOptionProperties("");

        assertTrue(propertiesForEmptyLongName.isEmpty());
    }
}
