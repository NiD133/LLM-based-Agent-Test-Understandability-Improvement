package org.apache.commons.io.input.buffer;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CircularByteBuffer_ESTest_test15 extends CircularByteBuffer_ESTest_scaffolding {

    private static final int BUFFER_CAPACITY = 260;
    private static final int ONE_BYTE_ARRAY_SIZE = 1;
    private static final int INVALID_NEGATIVE_OFFSET = -2988;
    private static final byte PEEK_LENGTH = 53;

    @Test(timeout = 4000)
    public void test15() throws Throwable {
        CircularByteBuffer circularByteBuffer = new CircularByteBuffer(BUFFER_CAPACITY);
        byte[] sourceBytes = new byte[ONE_BYTE_ARRAY_SIZE];

        try {
            circularByteBuffer.peek(sourceBytes, INVALID_NEGATIVE_OFFSET, PEEK_LENGTH);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            verifyException("org.apache.commons.io.input.buffer.CircularByteBuffer", e);
        }
    }
}
