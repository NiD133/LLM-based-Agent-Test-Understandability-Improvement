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
public class PosixParser_ESTest_test11 extends PosixParser_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test11() throws Throwable {
        PosixParser posixParser0 = new PosixParser();
        Options options0 = new Options();
        String[] stringArray0 = new String[6];
        Properties properties0 = new Properties();
        posixParser0.parse(options0, stringArray0, properties0, true);
        Option option0 = new Option("Z", true, "");
        options0.addOption(option0);
        posixParser0.burstToken("wZ", true);
    }
}
