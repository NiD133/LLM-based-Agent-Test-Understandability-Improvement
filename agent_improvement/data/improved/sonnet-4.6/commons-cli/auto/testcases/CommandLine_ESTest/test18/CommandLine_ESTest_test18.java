package org.apache.commons.cli;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.shaded.org.mockito.Mockito.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.function.Consumer;
import java.util.function.Supplier;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.ViolatedAssumptionAnswer;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CommandLine_ESTest_test18 extends CommandLine_ESTest_scaffolding {

    /**
     * Verifies that getOptionValue returns the processed option value (not the default)
     * when the option has had a value set via processValue.
     */
    @Test(timeout = 4000)
    public void test_getOptionValue_returnsProcessedValue_whenOptionValueIsSet() throws Throwable {
        CommandLine commandLine = new CommandLine();

        // Create an option that accepts an argument, identified by short/long name "options"
        Option outputOption = new Option("options", "options", true, "options");
        commandLine.addOption(outputOption);

        // Simulate the parser setting the option's value
        outputOption.processValue("options");

        // getOptionValue should return the processed value "options", not the default "options"
        String actualValue = commandLine.getOptionValue("options", "options");

        assertEquals("options", actualValue);
    }
}
