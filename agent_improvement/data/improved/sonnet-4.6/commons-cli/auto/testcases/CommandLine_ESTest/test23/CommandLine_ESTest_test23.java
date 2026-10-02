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
public class CommandLine_ESTest_test23 extends CommandLine_ESTest_scaffolding {

    /**
     * Verifies that getOptionValue on an OptionGroup returns the value of the selected option
     * even when no default Supplier is provided (null Supplier).
     */
    @Test(timeout = 4000)
    public void test_getOptionValueFromOptionGroup_returnsSelectedOptionValue_whenDefaultSupplierIsNull() throws Throwable {
        CommandLine commandLine = new CommandLine();

        // Create an option with short name "options", long name "options", that accepts an argument
        Option option = new Option("options", "options", true, (String) null);
        commandLine.addOption(option);

        // Simulate the option being given the value "options" on the command line
        option.processValue("options");

        // Select this option within a group to simulate mutual exclusion
        OptionGroup optionGroup = new OptionGroup();
        optionGroup.setSelected(option);

        // Retrieve the option value via the group; null Supplier means no fallback default
        Supplier<String> noDefault = null;
        String actualValue = commandLine.getOptionValue(optionGroup, noDefault);

        assertEquals("options", actualValue);
    }
}
