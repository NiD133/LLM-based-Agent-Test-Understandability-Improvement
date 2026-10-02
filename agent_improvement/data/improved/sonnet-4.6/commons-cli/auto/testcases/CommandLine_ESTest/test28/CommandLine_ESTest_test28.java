package org.apache.commons.cli;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CommandLine_ESTest_test28 extends CommandLine_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test28_addArgReturnsTheSameBuilderInstance() throws Throwable {
        CommandLine.Builder builder = CommandLine.builder();
        CommandLine.Builder returnedBuilder = builder.addArg("2&jM^W@]Ux%2T.zg ");
        assertSame(returnedBuilder, builder);
    }
}
