package org.apache.commons.cli;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CommandLine_ESTest_test29 extends CommandLine_ESTest_scaffolding {

    /**
     * Verifies that {@link CommandLine.Builder#addArg(String)} returns the same
     * builder instance, enabling fluent method chaining, even when given a null argument.
     */
    @Test(timeout = 4000)
    public void addArgWithNullReturnsSameBuilderForChaining() throws Throwable {
        CommandLine.Builder builder = CommandLine.builder();

        CommandLine.Builder returnedBuilder = builder.addArg((String) null);

        assertSame(builder, returnedBuilder);
    }
}
