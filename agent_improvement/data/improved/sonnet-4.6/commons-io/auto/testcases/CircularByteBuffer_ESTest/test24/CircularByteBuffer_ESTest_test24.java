package org.apache.commons.io.input.buffer;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CircularByteBuffer_ESTest_test24 extends CircularByteBuffer_ESTest_scaffolding {

    // offset 28 exceeds the array length of 16, so add() must reject it
    private static final int ARRAY_LENGTH = 16;
    private static final int OUT_OF_BOUNDS_OFFSET = 28;
    private static final int LENGTH = 28;

    @Test(timeout = 4000)
    public void test_add_throwsIllegalArgumentException_whenOffsetExceedsArrayLength() throws Throwable {
        byte[] smallBuffer = new byte[ARRAY_LENGTH];
        CircularByteBuffer circularByteBuffer = new CircularByteBuffer();

        try {
            circularByteBuffer.add(smallBuffer, OUT_OF_BOUNDS_OFFSET, LENGTH);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            //
            // Illegal offset: 28
            //
            verifyException("org.apache.commons.io.input.buffer.CircularByteBuffer", e);
        }
    }
}
