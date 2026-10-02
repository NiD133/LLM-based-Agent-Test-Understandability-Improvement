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
public class PosixParser_ESTest_test02 extends PosixParser_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test02() throws Throwable {
        PosixParser posixParser0 = new PosixParser();
        Options options0 = new Options();
        String[] stringArray0 = new String[6];
        Option option0 = new Option("Z", true, "");
        Options options1 = options0.addOption(option0);
        stringArray0[4] = "-Z&=";
        String[] stringArray1 = posixParser0.flatten(options1, stringArray0, true);
        String[] stringArray2 = posixParser0.flatten(options1, stringArray1, true);
        assertEquals(2, stringArray2.length);
    }
}
