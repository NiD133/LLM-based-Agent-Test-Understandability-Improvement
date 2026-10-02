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

    /**
     * A CommandLine built without adding any arguments should expose an empty
     * argument list from {@link CommandLine#getArgList()}.
     */
    @Test(timeout = 4000)
    public void getArgListIsEmptyForFreshlyBuiltCommandLine() throws Throwable {
        CommandLine commandLine = CommandLine.builder().get();

        List<String> argList = commandLine.getArgList();

        assertTrue("A command line with no arguments should have an empty argument list", argList.isEmpty());
    }
}
