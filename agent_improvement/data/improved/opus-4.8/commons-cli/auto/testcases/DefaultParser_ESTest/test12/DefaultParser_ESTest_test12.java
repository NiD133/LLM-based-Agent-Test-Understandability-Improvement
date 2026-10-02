package org.apache.commons.cli;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class DefaultParser_ESTest_test12 extends DefaultParser_ESTest_scaffolding {

    /**
     * Parsing a lone "-" token (a bare hyphen, with no options defined and
     * stopAtNonOption enabled) is treated as a non-option argument rather than
     * an error, so the parser still returns a valid CommandLine.
     */
    @Test(timeout = 4000)
    public void parsingBareHyphenWithStopAtNonOptionReturnsCommandLine() throws Throwable {
        DefaultParser parser = new DefaultParser();
        Options noOptions = new Options();
        String[] arguments = {"-"};

        CommandLine commandLine = parser.parse(noOptions, arguments, true);

        assertNotNull(commandLine);
    }
}
