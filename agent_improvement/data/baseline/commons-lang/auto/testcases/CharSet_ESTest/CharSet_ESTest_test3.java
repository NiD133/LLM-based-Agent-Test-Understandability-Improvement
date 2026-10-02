package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Set;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CharSet_ESTest_test3 extends CharSet_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test3() throws Throwable {
        String[] stringArray0 = new String[6];
        stringArray0[4] = "=]w9^0fV";
        CharSet charSet0 = new CharSet(stringArray0);
    }
}
