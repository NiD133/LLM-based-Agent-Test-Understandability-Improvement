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
public class PosixParser_ESTest_test00 extends PosixParser_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test00() throws Throwable {
        final PosixParser parser = new PosixParser();
        final Options noConfiguredOptions = new Options();

        final String[] arguments = new String[2];
        arguments[0] = "-D";

        final CommandLine parsedCommandLine = parser.parse(noConfiguredOptions, arguments, true);

        assertNotNull(parsedCommandLine);
    }
}
