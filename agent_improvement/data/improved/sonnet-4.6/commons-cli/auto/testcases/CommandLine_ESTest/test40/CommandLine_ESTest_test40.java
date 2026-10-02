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
public class CommandLine_ESTest_test40 extends CommandLine_ESTest_scaffolding {

    /**
     * When option '0' has not been added to the CommandLine and the default-value
     * supplier is null, getParsedOptionValues must return null rather than throwing.
     */
    @Test(timeout = 4000)
    public void getParsedOptionValues_withUnregisteredOptionAndNullDefaultSupplier_returnsNull() throws Throwable {
        CommandLine emptyCommandLine = new CommandLine();

        Supplier<Class<Option>[]> nullDefaultSupplier = null;
        Class<Option>[] result = emptyCommandLine.getParsedOptionValues('0', nullDefaultSupplier);

        assertNull(result);
    }
}
