package org.apache.commons.cli;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class DefaultParser_ESTest_test28 extends DefaultParser_ESTest_scaffolding {

    /**
     * Parsing an unrecognized dash-prefixed token with stopAtNonOption enabled
     * should not throw: the parser stops at the unknown token instead of failing,
     * and returns a non-null CommandLine.
     */
    @Test(timeout = 4000)
    public void parseWithStopAtNonOptionReturnsCommandLine() throws Throwable {
        Options emptyOptions = new Options();
        DefaultParser parser = new DefaultParser();

        // The first argument is an unknown short-option-like token; the remaining
        // 13 entries stay null. With stopAtNonOption = true the parser tolerates it.
        String[] arguments = new String[14];
        arguments[0] = "-s-#\"J";
        boolean stopAtNonOption = true;

        CommandLine commandLine = parser.parse(emptyOptions, arguments, stopAtNonOption);

        assertNotNull(commandLine);
    }
}
