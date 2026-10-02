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
     * When getParsedOptionValue is called with a char option that was never added to the
     * CommandLine, and a null Supplier as fallback, the method should return null because
     * neither the option value nor a default can be resolved.
     */
    @Test(timeout = 4000)
    public void test_getParsedOptionValue_withUnsetCharOption_andNullSupplier_returnsNull() throws Throwable {
        CommandLine commandLine = new CommandLine();
        Supplier<Class<Option>> nullDefaultSupplier = null;

        Class<Option> result = commandLine.getParsedOptionValue('a', nullDefaultSupplier);

        assertNull(result);
    }
}
