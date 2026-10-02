package org.apache.commons.io.input.buffer;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CircularByteBuffer_ESTest_test11 extends CircularByteBuffer_ESTest_scaffolding {

    /**
     * peek() must reject a requested length that exceeds the buffer's capacity.
     * Here the buffer can hold only 7 bytes, but the requested length is 27,
     * so peek() is expected to throw IllegalArgumentException ("Illegal length: 27").
     */
    @Test(timeout = 4000)
    public void peekWithLengthLargerThanBufferThrowsIllegalArgumentException() throws Throwable {
        final int bufferCapacity = 7;
        final int requestedLength = 27;
        CircularByteBuffer buffer = new CircularByteBuffer(bufferCapacity);

        byte[] target = new byte[16];
        int offset = 7;

        try {
            buffer.peek(target, offset, requestedLength);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // peek() rejects requestedLength (27) because it is greater than the
            // buffer capacity (7): "Illegal length: 27".
            verifyException("org.apache.commons.io.input.buffer.CircularByteBuffer", e);
        }
    }
}
