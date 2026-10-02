package org.apache.commons.cli;

import static org.junit.Assert.assertSame;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CommandLine_ESTest_test27 extends CommandLine_ESTest_scaffolding {

    /**
     * Verifies that {@link CommandLine.Builder#addOption(Option)} returns the same
     * builder instance, enabling fluent method chaining. Passing a {@code null}
     * option is a no-op but must still return {@code this}.
     */
    @Test(timeout = 4000)
    public void addOptionWithNullReturnsSameBuilderForChaining() throws Throwable {
        CommandLine.Builder builder = CommandLine.builder();

        CommandLine.Builder returnedBuilder = builder.addOption((Option) null);

        assertSame(builder, returnedBuilder);
    }
}
