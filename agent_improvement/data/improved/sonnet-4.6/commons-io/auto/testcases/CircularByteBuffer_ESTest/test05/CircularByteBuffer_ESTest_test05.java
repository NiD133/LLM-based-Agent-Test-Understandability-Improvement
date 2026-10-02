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
     * Verifies that reading more bytes than the buffer currently holds throws
     * IllegalStateException. The buffer is empty (0 bytes), but the caller
     * requests 3 bytes — this must be rejected.
     */
    @Test(timeout = 4000)
    public void test05() throws Throwable {
        CircularByteBuffer emptyBuffer = new CircularByteBuffer();
        byte[] destination = new byte[6];
        int destinationOffset = 2;
        int bytesToRead = 3;

        // Undeclared exception!
        try {
            emptyBuffer.read(destination, destinationOffset, bytesToRead);
            fail("Expecting exception: IllegalStateException");
        } catch (IllegalStateException e) {
            // Buffer has 0 bytes but 3 were requested — exception message reflects the mismatch
            verifyException("org.apache.commons.io.input.buffer.CircularByteBuffer", e);
        }
    }
}
