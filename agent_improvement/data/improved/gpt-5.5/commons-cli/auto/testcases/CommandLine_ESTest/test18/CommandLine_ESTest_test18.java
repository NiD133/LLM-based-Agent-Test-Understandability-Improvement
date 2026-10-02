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

    private static final String OPTION_NAME = "options";
    private static final String OPTION_DESCRIPTION = "options";
    private static final String OPTION_VALUE = "options";
    private static final String DEFAULT_VALUE = "options";

    @Test(timeout = 4000)
    public void test18() throws Throwable {
        CommandLine commandLine = new CommandLine();
        Option optionWithArgument = new Option(OPTION_NAME, OPTION_NAME, true, OPTION_DESCRIPTION);

        commandLine.addOption(optionWithArgument);
        optionWithArgument.processValue(OPTION_VALUE);

        String resolvedValue = commandLine.getOptionValue(OPTION_NAME, DEFAULT_VALUE);

        assertEquals(OPTION_VALUE, resolvedValue);
    }
}
