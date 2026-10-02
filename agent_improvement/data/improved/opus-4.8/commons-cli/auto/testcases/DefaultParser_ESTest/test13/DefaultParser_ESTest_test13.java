package org.apache.commons.cli;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class DefaultParser_ESTest_test13 extends DefaultParser_ESTest_scaffolding {

    /**
     * Parsing a lone "--" token (the conventional end-of-options marker) should
     * succeed and return a non-null CommandLine, even with no options defined and
     * stopAtNonOption disabled. The "--" simply switches the parser into
     * pass-through mode, leaving nothing further to parse.
     */
    @Test(timeout = 4000)
    public void parseEndOfOptionsMarkerReturnsCommandLine() throws Throwable {
        DefaultParser parser = new DefaultParser();
        Options noOptions = new Options();
        String[] arguments = { "--" };

        CommandLine commandLine = parser.parse(noOptions, arguments, false);

        assertNotNull(commandLine);
    }
}
