package org.apache.commons.cli;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CommandLine_ESTest_test03 extends CommandLine_ESTest_scaffolding {

    /**
     * Verifies that {@link CommandLine#getOptionCount(char)} returns 0 when the
     * command line contains an unrelated option and the queried character ('h')
     * matches none of the present options.
     */
    @Test(timeout = 4000)
    public void getOptionCountReturnsZeroForAbsentOption() throws Throwable {
        // Build a command line whose only option has the long name "Options".
        Option unrelatedOption = new Option((String) null, "Options");
        CommandLine commandLine = CommandLine.builder()
                .addOption(unrelatedOption)
                .get();

        // 'h' does not match the option above, so its count must be 0.
        int countForH = commandLine.getOptionCount('h');

        assertEquals(0, countForH);
    }
}
