package org.apache.commons.cli;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CommandLine_ESTest_test43 extends CommandLine_ESTest_scaffolding {

    /**
     * When the option 'M' is not present on an empty command line,
     * getParsedOptionValue returns the supplied default value (here, null).
     */
    @Test(timeout = 4000)
    public void getParsedOptionValue_withMissingOption_returnsNullDefault() throws Throwable {
        CommandLine emptyCommandLine = new CommandLine();
        Option defaultValue = null;

        Option parsedValue = emptyCommandLine.getParsedOptionValue('M', defaultValue);

        assertNull(parsedValue);
    }
}
