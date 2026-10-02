package org.apache.commons.cli;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class PosixParser_ESTest_test07 extends PosixParser_ESTest_scaffolding {

    /**
     * Parsing should succeed when no options are defined and the arguments contain
     * only a bare single hyphen "-" among otherwise empty (null) entries. The parser
     * treats "-" as a plain token and returns a non-null CommandLine.
     */
    @Test(timeout = 4000)
    public void parseWithBareHyphenAndNoOptionsReturnsCommandLine() throws Throwable {
        String[] arguments = new String[65];
        arguments[4] = "-";

        Options noOptions = new Options();
        PosixParser parser = new PosixParser();

        CommandLine commandLine = parser.parse(noOptions, arguments);

        assertNotNull(commandLine);
    }
}
