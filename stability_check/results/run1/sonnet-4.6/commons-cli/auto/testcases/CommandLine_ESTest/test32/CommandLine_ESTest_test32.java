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
public class CommandLine_ESTest_test32 extends CommandLine_ESTest_scaffolding {

    /**
     * When option 'a' is absent and the default-value supplier is null,
     * getParsedOptionValue should return null rather than throw.
     */
    @Test(timeout = 4000)
    public void test_getParsedOptionValue_absentOption_withNullSupplier_returnsNull() throws Throwable {
        CommandLine commandLine = new CommandLine();
        Supplier<Class<Option>> nullDefaultSupplier = null;
        Class<Option> result = commandLine.getParsedOptionValue('a', nullDefaultSupplier);
        assertNull(result);
    }
}
