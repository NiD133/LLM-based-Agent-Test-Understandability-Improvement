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
public class CommandLine_ESTest_test17 extends CommandLine_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test17() throws Throwable {
        final String optionName = "GPmL";
        final String defaultValue = "GPmL";

        CommandLine commandLine = new CommandLine();
        Option registeredOptionWithoutValue = new Option(optionName, optionName);
        commandLine.addOption(registeredOptionWithoutValue);

        Option.Builder matchingOptionBuilder = Option.builder(optionName);
        matchingOptionBuilder.longOpt(optionName);
        Option matchingOption = matchingOptionBuilder.get();

        String optionValue = commandLine.getOptionValue(matchingOption, defaultValue);

        assertEquals(defaultValue, optionValue);
    }
}
