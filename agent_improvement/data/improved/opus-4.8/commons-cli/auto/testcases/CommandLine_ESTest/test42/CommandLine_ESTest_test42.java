package org.apache.commons.cli;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.function.Supplier;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CommandLine_ESTest_test42 extends CommandLine_ESTest_scaffolding {

    /**
     * When the command line has no options, looking up the value of an unknown
     * option character returns the supplied default. Here the default supplier
     * is null, so the result must be null.
     */
    @Test(timeout = 4000)
    public void getOptionValueWithNullDefaultSupplierReturnsNull() throws Throwable {
        CommandLine emptyCommandLine = CommandLine.builder().get();

        Supplier<String> nullDefaultSupplier = null;
        String optionValue = emptyCommandLine.getOptionValue('_', nullDefaultSupplier);

        assertNull(optionValue);
    }
}
