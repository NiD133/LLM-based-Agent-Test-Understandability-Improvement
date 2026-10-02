package org.apache.commons.cli;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CommandLine_ESTest_test37 extends CommandLine_ESTest_scaffolding {

    /**
     * An empty CommandLine should report that no option is present, even when
     * queried by a single character name such as 'T'.
     */
    @Test(timeout = 4000)
    public void hasOption_onEmptyCommandLine_returnsFalse() throws Throwable {
        CommandLine emptyCommandLine = new CommandLine();

        boolean optionPresent = emptyCommandLine.hasOption('T');

        assertFalse(optionPresent);
    }
}
