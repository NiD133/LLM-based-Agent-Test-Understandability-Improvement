package org.apache.commons.cli;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CommandLine_ESTest_test26 extends CommandLine_ESTest_scaffolding {

    /**
     * Verifies that adding a {@code null} argument to a freshly created
     * {@link CommandLine} is silently ignored and does not raise an exception.
     */
    @Test(timeout = 4000)
    public void addArg_withNullArgument_isIgnoredWithoutError() throws Throwable {
        CommandLine commandLine = new CommandLine();

        commandLine.addArg((String) null);
    }
}
