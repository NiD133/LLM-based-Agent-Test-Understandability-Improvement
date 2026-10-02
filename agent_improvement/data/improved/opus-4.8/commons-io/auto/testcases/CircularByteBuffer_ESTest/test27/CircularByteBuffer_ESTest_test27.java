package org.apache.commons.io.input.buffer;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CircularByteBuffer_ESTest_test27 extends CircularByteBuffer_ESTest_scaffolding {

    /**
     * Adding more bytes than the buffer's capacity must fail: once a buffer of
     * size 2 holds 2 bytes, a third {@code add} should throw
     * {@link IllegalStateException} ("No space available").
     */
    @Test(timeout = 4000)
    public void addingBeyondCapacityThrowsIllegalStateException() throws Throwable {
        final int capacity = 2;
        CircularByteBuffer buffer = new CircularByteBuffer(capacity);

        // Fill the buffer to its full capacity.
        buffer.add((byte) -14);
        buffer.add((byte) 0);

        // The buffer is now full, so one more add must be rejected.
        try {
            buffer.add((byte) -14);
            fail("Expecting exception: IllegalStateException");
        } catch (IllegalStateException e) {
            // Thrown by CircularByteBuffer.add with message "No space available".
            verifyException("org.apache.commons.io.input.buffer.CircularByteBuffer", e);
        }
    }
}
