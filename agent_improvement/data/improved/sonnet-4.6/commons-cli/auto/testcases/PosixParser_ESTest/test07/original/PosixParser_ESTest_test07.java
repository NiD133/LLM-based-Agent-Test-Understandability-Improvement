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

    @Test(timeout = 4000)
    public void test07() throws Throwable {
        String[] stringArray0 = new String[65];
        stringArray0[4] = "-";
        Options options0 = new Options();
        PosixParser posixParser0 = new PosixParser();
        CommandLine commandLine0 = posixParser0.parse(options0, stringArray0);
        assertNotNull(commandLine0);
    }
}
