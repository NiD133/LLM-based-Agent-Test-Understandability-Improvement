package org.apache.commons.compress.compressors.lzma;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class LZMAUtils_ESTest_test01 extends LZMAUtils_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test01() throws Throwable {
        byte[] byteArray0 = new byte[6];
        byteArray0[0] = (byte) 93;
        boolean boolean0 = LZMAUtils.matches(byteArray0, 3);
        assertTrue(boolean0);
    }
}
