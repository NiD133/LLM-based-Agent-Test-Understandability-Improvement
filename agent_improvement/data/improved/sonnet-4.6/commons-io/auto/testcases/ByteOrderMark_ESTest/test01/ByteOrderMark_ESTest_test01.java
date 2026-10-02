package org.apache.commons.io;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class ByteOrderMark_ESTest_test01 extends ByteOrderMark_ESTest_scaffolding {

    /**
     * UTF-32LE BOM bytes are {0xFF, 0xFE, 0x00, 0x00}.
     * An all-zero array does not start with 0xFF, so matches() must return false.
     */
    @Test(timeout = 4000)
    public void test01() throws Throwable {
        ByteOrderMark utf32leBom = ByteOrderMark.UTF_32LE;
        int[] allZeroBytes = new int[7]; // default-initialised to 0, first byte 0 != 0xFF
        boolean matchesAllZeros = utf32leBom.matches(allZeroBytes);
        assertFalse("UTF-32LE BOM should not match an all-zero byte array", matchesAllZeros);
    }
}
