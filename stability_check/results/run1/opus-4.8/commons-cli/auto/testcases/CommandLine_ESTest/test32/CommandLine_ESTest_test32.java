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
     * When no options have been parsed, getParsedOptionValue for an unknown
     * option 'a' with a null default-value supplier should return null.
     */
    @Test(timeout = 4000)
    public void getParsedOptionValue_unknownOptionWithNullDefaultSupplier_returnsNull() throws Throwable {
        CommandLine emptyCommandLine = new CommandLine();
        Supplier<Class<Option>> nullDefaultSupplier = null;

        Class<Option> parsedValue = emptyCommandLine.getParsedOptionValue('a', nullDefaultSupplier);

        assertNull(parsedValue);
    }
}
