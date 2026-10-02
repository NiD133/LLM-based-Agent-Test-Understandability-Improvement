package org.apache.commons.io.input.buffer;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CircularByteBuffer_ESTest_test05 extends CircularByteBuffer_ESTest_scaffolding {

    /**
     * Reading more bytes than the buffer currently holds must fail.
     * A freshly constructed buffer is empty, so requesting 3 bytes from it
     * triggers an IllegalStateException raised by CircularByteBuffer itself.
     */
    @Test(timeout = 4000)
    public void readMoreBytesThanAvailableThrowsIllegalStateException() throws Throwable {
        CircularByteBuffer emptyBuffer = new CircularByteBuffer();
        byte[] target = new byte[6];

        int targetOffset = 2;
        int bytesToRead = 3;

        try {
            emptyBuffer.read(target, targetOffset, bytesToRead);
            fail("Expecting exception: IllegalStateException");
        } catch (IllegalStateException e) {
            // The buffer is empty (0 bytes), but 3 bytes were requested.
            verifyException("org.apache.commons.io.input.buffer.CircularByteBuffer", e);
        }
    }
}
