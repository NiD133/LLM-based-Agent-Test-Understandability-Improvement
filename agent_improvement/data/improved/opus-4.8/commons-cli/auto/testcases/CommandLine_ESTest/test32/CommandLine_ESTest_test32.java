package org.apache.commons.cli;

import static org.junit.Assert.assertNull;

import java.util.function.Supplier;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CommandLine_ESTest_test32 extends CommandLine_ESTest_scaffolding {

    /**
     * When an option has not been parsed, getParsedOptionValue should fall back
     * to the supplied default. A null default supplier yields a null result.
     */
    @Test(timeout = 4000)
    public void getParsedOptionValueWithNullDefaultSupplierReturnsNull() throws Throwable {
        CommandLine emptyCommandLine = new CommandLine();

        Supplier<Class<Option>> nullDefaultSupplier = null;
        Class<Option> parsedValue = emptyCommandLine.getParsedOptionValue('a', nullDefaultSupplier);

        assertNull(parsedValue);
    }
}
