package org.apache.commons.io.input.buffer;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CircularByteBuffer_ESTest_test03 extends CircularByteBuffer_ESTest_scaffolding {

    /**
     * Reading with a negative length must be rejected with an
     * IllegalArgumentException ("Illegal length: -14"), regardless of the
     * target buffer or offset.
     */
    @Test(timeout = 4000)
    public void readWithNegativeLengthThrowsIllegalArgumentException() throws Throwable {
        CircularByteBuffer buffer = new CircularByteBuffer(2);
        byte[] targetBuffer = new byte[3];
        int targetOffset = 2;
        int negativeLength = -14;

        try {
            buffer.read(targetBuffer, targetOffset, negativeLength);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // CircularByteBuffer rejects the negative length with "Illegal length: -14"
            verifyException("org.apache.commons.io.input.buffer.CircularByteBuffer", e);
        }
    }
}
