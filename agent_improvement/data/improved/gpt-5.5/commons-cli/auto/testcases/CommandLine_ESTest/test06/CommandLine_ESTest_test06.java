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
public class CommandLine_ESTest_test06 extends CommandLine_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test06() throws Throwable {
        String optionLongName = "mf";
        String optionValue = "2&jM^W@]Ux%2T.zg ";

        CommandLine commandLine = new CommandLine();
        Option optionWithArgument = new Option((String) null, optionLongName, true, optionValue);

        commandLine.addOption(optionWithArgument);
        optionWithArgument.processValue(optionValue);

        Properties optionProperties = commandLine.getOptionProperties(optionWithArgument);
        assertEquals(1, optionProperties.size());
    }
}
