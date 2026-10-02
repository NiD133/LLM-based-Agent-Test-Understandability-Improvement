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
public class CommandLine_ESTest_test05 extends CommandLine_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test05() throws Throwable {
        final String optionLongName = "mf";
        final String optionDescription = "&jM^W@]Ux%2T.zg ";
        final String firstProcessedValue = optionDescription;
        final String secondProcessedValue = "R({[";

        CommandLine commandLine = new CommandLine();
        Option option = new Option((String) null, optionLongName, true, optionDescription);

        option.setArgs(320);
        commandLine.addOption(option);

        option.processValue(firstProcessedValue);
        option.processValue(secondProcessedValue);

        Properties optionProperties = commandLine.getOptionProperties(option);
        assertEquals(1, optionProperties.size());
    }
}
