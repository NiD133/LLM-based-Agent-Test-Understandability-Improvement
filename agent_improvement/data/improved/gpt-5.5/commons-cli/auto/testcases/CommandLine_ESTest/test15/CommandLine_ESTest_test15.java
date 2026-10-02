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
public class CommandLine_ESTest_test15 extends CommandLine_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test15() throws Throwable {
        final CommandLine.Builder builder = CommandLine.builder();
        final CommandLine commandLine = builder.get();

        @SuppressWarnings("unchecked")
        final Supplier<String> defaultValueSupplier = (Supplier<String>) mock(Supplier.class, new ViolatedAssumptionAnswer());
        doReturn((Object) null).when(defaultValueSupplier).get();

        // A null OptionGroup has no selected option, so the null supplier result is returned.
        final String optionValue = commandLine.getOptionValue((OptionGroup) null, defaultValueSupplier);

        assertNull(optionValue);
    }
}
