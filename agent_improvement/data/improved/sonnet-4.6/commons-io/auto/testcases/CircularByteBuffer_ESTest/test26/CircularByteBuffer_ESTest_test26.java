package org.apache.commons.io.input.buffer;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CircularByteBuffer_ESTest_test26 extends CircularByteBuffer_ESTest_scaffolding {

    /**
     * Verifies that add(byte[], offset, length) throws IllegalArgumentException
     * when the offset is negative, because a negative offset is out of bounds
     * for any byte array.
     */
    @Test(timeout = 4000)
    public void test26_addWithNegativeOffsetThrowsIllegalArgumentException() throws Throwable {
        CircularByteBuffer buffer = new CircularByteBuffer(1);
        byte[] sourceData = new byte[3];
        int negativeOffset = -2153;
        int length = (int) (byte) 4; // 4

        try {
            buffer.add(sourceData, negativeOffset, length);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            //
            // Illegal offset: -2153
            //
            verifyException("org.apache.commons.io.input.buffer.CircularByteBuffer", e);
        }
    }
}
