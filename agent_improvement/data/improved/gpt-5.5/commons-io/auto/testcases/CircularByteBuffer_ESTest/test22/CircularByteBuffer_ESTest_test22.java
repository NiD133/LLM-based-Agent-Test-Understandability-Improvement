package org.apache.commons.io.input.buffer;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CircularByteBuffer_ESTest_test22 extends CircularByteBuffer_ESTest_scaffolding {

    private static final int SOURCE_BUFFER_SIZE = 16;
    private static final byte CIRCULAR_BUFFER_CAPACITY = 8;
    private static final int SOURCE_OFFSET = 8;
    private static final int BYTES_TO_ADD = 1783;

    @Test(timeout = 4000)
    public void test22() throws Throwable {
        byte[] sourceBuffer = new byte[SOURCE_BUFFER_SIZE];
        CircularByteBuffer circularByteBuffer = new CircularByteBuffer(CIRCULAR_BUFFER_CAPACITY);

        try {
            circularByteBuffer.add(sourceBuffer, SOURCE_OFFSET, BYTES_TO_ADD);
            fail("Expecting exception: IllegalStateException");
        } catch (IllegalStateException e) {
            verifyException("org.apache.commons.io.input.buffer.CircularByteBuffer", e);
        }
    }
}
