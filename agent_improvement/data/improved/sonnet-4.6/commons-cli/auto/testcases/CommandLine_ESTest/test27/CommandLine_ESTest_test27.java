package org.apache.commons.cli;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CommandLine_ESTest_test27 extends CommandLine_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void addNullOption_builderReturnsSameInstanceForChaining() throws Throwable {
        CommandLine.Builder builder = CommandLine.builder();
        CommandLine.Builder returnedBuilder = builder.addOption((Option) null);
        assertSame("addOption(null) should return the same Builder instance to support method chaining",
                builder, returnedBuilder);
    }
}
