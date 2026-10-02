package org.apache.commons.cli;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.List;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CommandLine_ESTest_test35 extends CommandLine_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test_newCommandLineHasEmptyArgList() throws Throwable {
        CommandLine.Builder builder = CommandLine.builder();
        CommandLine commandLine = builder.get();

        List<String> argList = commandLine.getArgList();

        assertTrue(argList.isEmpty());
    }
}
