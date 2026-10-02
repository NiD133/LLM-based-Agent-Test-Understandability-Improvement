package org.apache.commons.cli;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CommandLine_ESTest_test24 extends CommandLine_ESTest_scaffolding {

    /**
     * Verifies that adding a {@code null} option is silently ignored:
     * {@link CommandLine#addOption(Option)} guards against {@code null} and
     * simply returns without modifying the command line, so no exception is thrown.
     */
    @Test(timeout = 4000)
    public void addNullOptionIsIgnored() throws Throwable {
        CommandLine commandLine = new CommandLine();

        commandLine.addOption((Option) null);
    }
}
