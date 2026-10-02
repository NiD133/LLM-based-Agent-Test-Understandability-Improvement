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
public class PosixParser_ESTest_test01 extends PosixParser_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test01() throws Throwable {
        String[] stringArray0 = new String[13];
        stringArray0[2] = "-bdKQ";
        Options options0 = new Options();
        Option option0 = new Option("bdKQ", false, "bdKQ");
        options0.addOption(option0);
        PosixParser posixParser0 = new PosixParser();
        CommandLine commandLine0 = posixParser0.parse(options0, stringArray0);
        assertNotNull(commandLine0);
    }
}
