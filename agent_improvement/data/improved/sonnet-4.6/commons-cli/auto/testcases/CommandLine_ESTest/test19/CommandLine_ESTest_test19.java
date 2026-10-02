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
public class CommandLine_ESTest_test19 extends CommandLine_ESTest_scaffolding {

    /**
     * Verifies that getOptionProperties returns an empty Properties (never null)
     * when the queried name does not match any registered option's short or long name.
     */
    @Test(timeout = 4000)
    public void test_getOptionProperties_returnsEmptyProperties_whenOptionNameNotFound() throws Throwable {
        CommandLine commandLine = new CommandLine();
        Option registeredOption = new Option("1", "1");
        commandLine.addOption(registeredOption);

        // Query with a name that does not match the registered option's opt ("1") or longOpt (null)
        Properties result = commandLine.getOptionProperties(":nx1");

        assertTrue(result.isEmpty());
    }
}
