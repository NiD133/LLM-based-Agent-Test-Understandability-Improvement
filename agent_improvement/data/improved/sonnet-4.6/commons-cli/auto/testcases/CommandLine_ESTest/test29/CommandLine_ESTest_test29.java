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
     * Verifies that Builder.addArg(null) silently ignores the null argument
     * and returns the same Builder instance to support method chaining.
     */
    @Test(timeout = 4000)
    public void test_addArgNull_returnsSameBuilderForChaining() throws Throwable {
        CommandLine.Builder builder = CommandLine.builder();
        CommandLine.Builder builderAfterNullArg = builder.addArg((String) null);
        assertSame(builderAfterNullArg, builder);
    }
}
