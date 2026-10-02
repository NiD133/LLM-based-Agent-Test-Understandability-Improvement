package org.apache.commons.cli;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CommandLine_ESTest_test04 extends CommandLine_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test04() throws Throwable {
        CommandLine commandLine = new CommandLine();
        String optionName = null;
        Object result = commandLine.getOptionObject(optionName);
        assertNull(result);
    }
}
