package org.apache.commons.io.input;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class UnsynchronizedByteArrayInputStream_ESTest_test17 extends UnsynchronizedByteArrayInputStream_ESTest_scaffolding {

    /**
     * Reading into a buffer the same size as the stream's data should consume
     * every byte: the call returns the number of bytes read and the stream is
     * left with nothing available.
     */
    @Test(timeout = 4000)
    public void readFullBufferConsumesAllBytesAndLeavesNothingAvailable() throws Throwable {
        byte[] buffer = new byte[2];
        UnsynchronizedByteArrayInputStream stream = new UnsynchronizedByteArrayInputStream(buffer);

        int bytesRead = stream.read(buffer);

        assertEquals("read(byte[]) should report all 2 bytes were read", 2, bytesRead);
        assertEquals("stream should be fully consumed after reading every byte", 0, stream.available());
    }
}
