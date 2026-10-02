package org.apache.commons.cli;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CommandLine_ESTest_test34 extends CommandLine_ESTest_scaffolding {

    /**
     * A freshly created CommandLine has no parsed options, so getOptions()
     * should return an empty array.
     */
    @Test(timeout = 4000)
    public void getOptionsOnNewCommandLineReturnsEmptyArray() throws Throwable {
        CommandLine commandLine = new CommandLine();

        Option[] options = commandLine.getOptions();

        assertEquals(0, options.length);
    }
}
