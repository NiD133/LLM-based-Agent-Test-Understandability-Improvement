package org.apache.commons.cli;

import static org.junit.Assert.assertNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CommandLine_ESTest_test38 extends CommandLine_ESTest_scaffolding {

    /**
     * Verifies that querying the option object for a short option that was never
     * added returns {@code null} rather than throwing.
     */
    @Test(timeout = 4000)
    public void getOptionObject_unknownOption_returnsNull() throws Throwable {
        CommandLine emptyCommandLine = new CommandLine();

        Object optionValue = emptyCommandLine.getOptionObject('H');

        assertNull("An option that was never set should have no parsed value", optionValue);
    }
}
