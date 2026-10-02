package org.apache.commons.cli;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.shaded.org.mockito.Mockito.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.util.function.Supplier;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.ViolatedAssumptionAnswer;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CommandLine_ESTest_test15 extends CommandLine_ESTest_scaffolding {

    /**
     * When the OptionGroup is null and the default-value supplier returns null,
     * getOptionValue should return null.
     */
    @Test(timeout = 4000)
    public void test15() throws Throwable {
        CommandLine commandLine = CommandLine.builder().get();

        Supplier<String> nullReturningSupplier = (Supplier<String>) mock(Supplier.class, new ViolatedAssumptionAnswer());
        doReturn((Object) null).when(nullReturningSupplier).get();

        String result = commandLine.getOptionValue((OptionGroup) null, nullReturningSupplier);

        assertNull(result);
    }
}
