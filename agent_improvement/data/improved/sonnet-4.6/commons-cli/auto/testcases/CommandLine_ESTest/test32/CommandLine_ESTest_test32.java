package org.apache.commons.cli;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.function.Supplier;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CommandLine_ESTest_test32 extends CommandLine_ESTest_scaffolding {

    /**
     * When the option 'a' is not present in the command line and the default-value
     * supplier is null, getParsedOptionValue should return null.
     */
    @Test(timeout = 4000)
    public void test_getParsedOptionValue_absentOptionWithNullSupplier_returnsNull() throws Throwable {
        CommandLine commandLine = new CommandLine();
        Supplier<Class<Option>> nullDefaultSupplier = null;

        Class<Option> result = commandLine.getParsedOptionValue('a', nullDefaultSupplier);

        assertNull(result);
    }
}
