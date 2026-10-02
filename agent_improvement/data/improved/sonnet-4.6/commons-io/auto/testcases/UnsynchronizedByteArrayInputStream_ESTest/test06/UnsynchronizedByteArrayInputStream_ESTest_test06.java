package org.apache.commons.io.input;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class UnsynchronizedByteArrayInputStream_ESTest_test06 extends UnsynchronizedByteArrayInputStream_ESTest_scaffolding {

    // Reading zero bytes from a stream whose offset exceeds the buffer size returns 0 and leaves nothing available.
    @Test(timeout = 4000)
    public void test06() throws Throwable {
        byte[] buffer = new byte[2];
        // Offset 20 exceeds the 2-byte buffer, so the stream is positioned at end-of-data immediately.
        UnsynchronizedByteArrayInputStream inputStream = new UnsynchronizedByteArrayInputStream(buffer, (byte) 20);

        int bytesRead = inputStream.read(buffer, 0, 0);

        assertEquals(0, bytesRead);
        assertEquals(0, inputStream.available());
    }
}
