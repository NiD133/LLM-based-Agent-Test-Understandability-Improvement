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

    @Test(timeout = 4000)
    public void test42() throws Throwable {
        // Build an empty CommandLine with no options or arguments
        CommandLine commandLine = CommandLine.builder().get();

        // Querying an absent option '_' with a null Supplier as default yields null,
        // because the option is not set and the null supplier provides no fallback value
        String result = commandLine.getOptionValue('_', (Supplier<String>) null);

        assertNull(result);
    }
}
