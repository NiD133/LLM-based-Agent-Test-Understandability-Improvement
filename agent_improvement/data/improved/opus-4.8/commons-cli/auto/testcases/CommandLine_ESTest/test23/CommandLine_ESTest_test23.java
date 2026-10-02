package org.apache.commons.cli;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.function.Supplier;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CommandLine_ESTest_test23 extends CommandLine_ESTest_scaffolding {

    /**
     * Verifies that {@link CommandLine#getOptionValue(OptionGroup, Supplier)} returns the value
     * of the group's selected option when that option has a parsed argument, ignoring the default
     * value supplier (here {@code null}) because a value is present.
     */
    @Test(timeout = 4000)
    public void getOptionValueForGroupReturnsSelectedOptionValue() throws Throwable {
        CommandLine commandLine = new CommandLine();

        // An option that takes an argument; both short and long name are "options".
        Option option = new Option("options", "options", true, (String) null);
        commandLine.addOption(option);

        // Record "options" as the parsed argument value for this option.
        option.processValue("options");

        // Make the option the selected member of a group.
        OptionGroup optionGroup = new OptionGroup();
        optionGroup.setSelected(option);

        // Default supplier is null, but it is unused since the selected option has a value.
        String value = commandLine.getOptionValue(optionGroup, (Supplier<String>) null);

        assertEquals("options", value);
    }
}
