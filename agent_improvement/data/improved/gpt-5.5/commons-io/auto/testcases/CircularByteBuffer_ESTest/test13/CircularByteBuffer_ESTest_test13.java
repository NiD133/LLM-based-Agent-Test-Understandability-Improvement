package org.apache.commons.io.input.buffer;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CircularByteBuffer_ESTest_test13 extends CircularByteBuffer_ESTest_scaffolding {

    private static final int SOURCE_BUFFER_LENGTH = 6;
    private static final int CIRCULAR_BUFFER_CAPACITY = 2;
    private static final int OFFSET_OUTSIDE_SOURCE_BUFFER = 1498;
    private static final int BYTES_TO_COMPARE = 1885;

    @Test(timeout = 4000)
    public void test13() throws Throwable {
        byte[] sourceBuffer = new byte[SOURCE_BUFFER_LENGTH];
        CircularByteBuffer circularBuffer = new CircularByteBuffer(CIRCULAR_BUFFER_CAPACITY);

        try {
            circularBuffer.peek(sourceBuffer, OFFSET_OUTSIDE_SOURCE_BUFFER, BYTES_TO_COMPARE);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // The invalid offset is rejected before the requested comparison length is evaluated.
            verifyException("org.apache.commons.io.input.buffer.CircularByteBuffer", e);
        }
    }
}
