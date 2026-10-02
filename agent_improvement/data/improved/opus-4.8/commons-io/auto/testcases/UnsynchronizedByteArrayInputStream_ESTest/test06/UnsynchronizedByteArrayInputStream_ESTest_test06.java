package org.apache.commons.io.input;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class UnsynchronizedByteArrayInputStream_ESTest_test06 extends UnsynchronizedByteArrayInputStream_ESTest_scaffolding {

    /**
     * When the requested length is zero, read(byte[], off, len) is a no-op that
     * returns 0 without consuming any bytes. Because the offset (20) is clamped
     * to the 2-byte buffer's bounds, the stream starts already exhausted, so
     * available() also reports 0.
     */
    @Test(timeout = 4000)
    public void readWithZeroLengthReturnsZeroAndConsumesNothing() throws Throwable {
        byte[] buffer = new byte[2];
        UnsynchronizedByteArrayInputStream stream =
                new UnsynchronizedByteArrayInputStream(buffer, 20);

        int bytesRead = stream.read(buffer, 0, 0);

        assertEquals("zero-length read should report no bytes read", 0, bytesRead);
        assertEquals("no bytes should remain available", 0, stream.available());
    }
}
