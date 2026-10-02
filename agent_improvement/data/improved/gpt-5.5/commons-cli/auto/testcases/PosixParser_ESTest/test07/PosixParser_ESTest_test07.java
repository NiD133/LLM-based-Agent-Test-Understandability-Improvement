package org.apache.commons.cli;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.util.Properties;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class PosixParser_ESTest_test07 extends PosixParser_ESTest_scaffolding {

    private static final int ARGUMENT_COUNT = 65;
    private static final int SINGLE_HYPHEN_INDEX = 4;
    private static final String SINGLE_HYPHEN = "-";

    @Test(timeout = 4000)
    public void test07() throws Throwable {
        String[] arguments = new String[ARGUMENT_COUNT];
        arguments[SINGLE_HYPHEN_INDEX] = SINGLE_HYPHEN;

        Options emptyOptions = new Options();
        PosixParser parser = new PosixParser();

        CommandLine commandLine = parser.parse(emptyOptions, arguments);

        assertNotNull(commandLine);
    }
}
