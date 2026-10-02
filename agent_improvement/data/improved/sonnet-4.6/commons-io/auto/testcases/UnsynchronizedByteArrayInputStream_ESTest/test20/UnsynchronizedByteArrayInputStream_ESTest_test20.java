package org.apache.commons.io.input;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class UnsynchronizedByteArrayInputStream_ESTest_test20 extends UnsynchronizedByteArrayInputStream_ESTest_scaffolding {

    /**
     * Verifies that when an offset beyond the buffer length is given, the stream
     * reports 0 available bytes even after calling mark().
     *
     * The buffer has 8 bytes but offset 358 exceeds it, so the internal position
     * is clamped to the end-of-buffer. After mark() is called (with any readLimit),
     * available() must still return 0 because no bytes remain to be read.
     */
    @Test(timeout = 4000)
    public void test20() throws Throwable {
        byte[] eightByteBuffer = new byte[8];
        int offsetBeyondBufferLength = 358;
        UnsynchronizedByteArrayInputStream stream =
                new UnsynchronizedByteArrayInputStream(eightByteBuffer, offsetBeyondBufferLength);

        // mark() with a negative readLimit is accepted by this implementation
        stream.mark(-1429);

        assertEquals(0, stream.available());
    }
}
