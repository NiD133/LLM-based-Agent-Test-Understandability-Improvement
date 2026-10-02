package org.apache.commons.cli;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CommandLine_ESTest_test25 extends CommandLine_ESTest_scaffolding {

    /**
     * Verifies that a non-null left-over argument can be added to a freshly
     * created {@link CommandLine} without raising any exception.
     */
    @Test(timeout = 4000)
    public void addArg_withNonNullArgument_completesWithoutError() throws Throwable {
        CommandLine commandLine = new CommandLine();

        commandLine.addArg("true");
    }
}
