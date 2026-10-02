package org.apache.commons.compress.utils;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class ByteUtils_ESTest_test09 extends ByteUtils_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test_fromLittleEndian_singleZeroByte_returnsZero() throws Throwable {
        // A one-element byte array whose sole byte is 0x00
        byte[] singleZeroByte = new byte[1];

        long result = ByteUtils.fromLittleEndian(singleZeroByte);

        assertEquals(0L, result);
    }
}
