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
public class CommandLine_ESTest_test22 extends CommandLine_ESTest_scaffolding {

    /**
     * Verifies that getOptionProperties returns an empty Properties when called
     * with a null Option, even if the CommandLine contains a registered option.
     * The null Option never matches any processed option, so no properties are populated.
     */
    @Test(timeout = 4000)
    public void test22() throws Throwable {
        Option option = new Option("L", "L");
        CommandLine commandLine = new CommandLine();
        commandLine.addOption(option);

        // Passing null Option: no processed option equals null, so result is empty
        Properties properties = commandLine.getOptionProperties((Option) null);

        assertTrue(properties.isEmpty());
    }
}
