package org.apache.commons.cli;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CommandLine_ESTest_test04 extends CommandLine_ESTest_scaffolding {

    /**
     * Looking up an option object by a null name on an empty command line
     * should return null rather than throw.
     */
    @Test(timeout = 4000)
    public void getOptionObjectWithNullNameReturnsNull() throws Throwable {
        CommandLine emptyCommandLine = new CommandLine();

        Object optionObject = emptyCommandLine.getOptionObject((String) null);

        assertNull(optionObject);
    }
}
