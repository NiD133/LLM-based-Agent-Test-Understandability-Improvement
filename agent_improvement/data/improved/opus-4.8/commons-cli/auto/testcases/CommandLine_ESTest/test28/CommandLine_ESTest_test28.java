package org.apache.commons.cli;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CommandLine_ESTest_test28 extends CommandLine_ESTest_scaffolding {

    /**
     * Verifies that {@link CommandLine.Builder#addArg(String)} supports fluent
     * chaining by returning the very same builder instance it was called on.
     */
    @Test(timeout = 4000)
    public void addArgReturnsSameBuilderForChaining() throws Throwable {
        CommandLine.Builder builder = CommandLine.builder();

        CommandLine.Builder returnedBuilder = builder.addArg("2&jM^W@]Ux%2T.zg ");

        assertSame(builder, returnedBuilder);
    }
}
