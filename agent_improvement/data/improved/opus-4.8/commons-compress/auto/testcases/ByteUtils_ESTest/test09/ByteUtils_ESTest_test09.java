package org.apache.commons.compress.utils;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class ByteUtils_ESTest_test09 extends ByteUtils_ESTest_scaffolding {

    /**
     * A single zero byte read as a little-endian long should yield 0.
     */
    @Test(timeout = 4000)
    public void fromLittleEndian_singleZeroByte_returnsZero() throws Throwable {
        byte[] singleZeroByte = new byte[1];

        long result = ByteUtils.fromLittleEndian(singleZeroByte);

        assertEquals(0L, result);
    }
}
