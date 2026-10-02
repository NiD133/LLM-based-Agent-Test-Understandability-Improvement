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
     * Reads a single little-endian byte from an input stream that holds one
     * zero byte. The decoded value should be 0, and the stream should be fully
     * consumed afterwards.
     */
    @Test(timeout = 4000)
    public void readSingleZeroByteFromStreamReturnsZero() throws Throwable {
        byte[] singleZeroByte = new byte[1];
        ByteArrayInputStream input = new ByteArrayInputStream(singleZeroByte);

        long decodedValue = ByteUtils.fromLittleEndian((InputStream) input, 1);

        assertEquals(0L, decodedValue);
        assertEquals("stream should be fully consumed", 0, input.available());
    }
}
