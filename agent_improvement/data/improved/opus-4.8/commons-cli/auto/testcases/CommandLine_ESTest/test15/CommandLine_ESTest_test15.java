package org.apache.commons.cli;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.shaded.org.mockito.Mockito.*;
import java.util.function.Supplier;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.ViolatedAssumptionAnswer;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CommandLine_ESTest_test15 extends CommandLine_ESTest_scaffolding {

    /**
     * When the option group is null, getOptionValue should fall back to the
     * supplied default value. Here the default-value supplier yields null, so
     * the returned option value is expected to be null.
     */
    @Test(timeout = 4000)
    public void getOptionValueWithNullGroupReturnsSuppliedDefault() throws Throwable {
        CommandLine commandLine = CommandLine.builder().get();

        @SuppressWarnings("unchecked")
        Supplier<String> defaultValueSupplier =
                (Supplier<String>) mock(Supplier.class, new ViolatedAssumptionAnswer());
        doReturn((Object) null).when(defaultValueSupplier).get();

        String optionValue = commandLine.getOptionValue((OptionGroup) null, defaultValueSupplier);

        assertNull(optionValue);
    }
}
