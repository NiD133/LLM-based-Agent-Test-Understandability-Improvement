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

    private static final String OPTION_NAME = "options";

    @Test(timeout = 4000)
    public void test23() throws Throwable {
        CommandLine commandLine = new CommandLine();
        OptionGroup optionGroup = new OptionGroup();
        Option selectedOption = new Option(OPTION_NAME, OPTION_NAME, true, (String) null);

        commandLine.addOption(selectedOption);
        selectedOption.processValue(OPTION_NAME);
        optionGroup.setSelected(selectedOption);

        String optionValue = commandLine.getOptionValue(optionGroup, (Supplier<String>) null);

        assertEquals(OPTION_NAME, optionValue);
    }
}
