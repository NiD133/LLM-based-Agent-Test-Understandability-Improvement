package org.apache.commons.compress.utils;

import org.junit.Test;
import static org.junit.Assert.*;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class ByteUtils_ESTest_test06 extends ByteUtils_ESTest_scaffolding {

    /**
     * Reading 1 byte from a stream containing a single zero byte should return 0
     * and leave no bytes remaining in the stream.
     */
    @Test(timeout = 4000)
    public void test06_fromLittleEndian_singleZeroByte_returnsZero() throws Throwable {
        byte[] singleZeroByte = new byte[1]; // default value is 0x00
        ByteArrayInputStream inputStream = new ByteArrayInputStream(singleZeroByte);

        long result = ByteUtils.fromLittleEndian((InputStream) inputStream, 1);

        assertEquals("Stream should be fully consumed after reading 1 byte", 0, inputStream.available());
        assertEquals("A single zero byte read as little-endian should equal 0", 0L, result);
    }
}
