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

    private static final int SOURCE_BUFFER_LENGTH = 16;
    private static final byte BUFFER_CAPACITY_AND_OFFSET = (byte) 7;
    private static final int TOO_LONG_PEEK_LENGTH = 27;

    @Test(timeout = 4000)
    public void test11() throws Throwable {
        byte[] sourceBuffer = new byte[SOURCE_BUFFER_LENGTH];
        CircularByteBuffer circularByteBuffer = new CircularByteBuffer(BUFFER_CAPACITY_AND_OFFSET);

        try {
            circularByteBuffer.peek(sourceBuffer, BUFFER_CAPACITY_AND_OFFSET, TOO_LONG_PEEK_LENGTH);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            verifyException("org.apache.commons.io.input.buffer.CircularByteBuffer", e);
        }
    }
}
