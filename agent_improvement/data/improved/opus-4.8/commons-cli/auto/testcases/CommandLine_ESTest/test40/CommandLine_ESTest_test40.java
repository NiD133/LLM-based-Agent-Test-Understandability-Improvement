package org.apache.commons.cli;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.function.Supplier;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CommandLine_ESTest_test40 extends CommandLine_ESTest_scaffolding {

    /**
     * Requesting parsed values for an option that is not present should return the
     * default value. Here the default-value supplier is {@code null}, so the method
     * yields {@code null}.
     */
    @Test(timeout = 4000)
    public void getParsedOptionValuesForUnknownOptionWithNullSupplierReturnsNull() throws Throwable {
        CommandLine emptyCommandLine = new CommandLine();
        Supplier<Class<Option>[]> nullDefaultValueSupplier = null;

        Class<Option>[] parsedValues =
                emptyCommandLine.getParsedOptionValues('0', nullDefaultValueSupplier);

        assertNull(parsedValues);
    }
}
