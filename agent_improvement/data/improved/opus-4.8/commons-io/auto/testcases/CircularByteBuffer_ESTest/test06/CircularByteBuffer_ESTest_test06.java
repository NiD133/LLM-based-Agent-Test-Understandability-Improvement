package org.apache.commons.io.input.buffer;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CircularByteBuffer_ESTest_test06 extends CircularByteBuffer_ESTest_scaffolding {

    /**
     * Verifies that {@link CircularByteBuffer#read(byte[], int, int)} rejects a
     * negative target offset by throwing an {@link IllegalArgumentException}.
     */
    @Test(timeout = 4000)
    public void readWithNegativeOffsetThrowsIllegalArgumentException() throws Throwable {
        CircularByteBuffer buffer = new CircularByteBuffer(1);
        byte[] targetBuffer = new byte[8];

        int negativeOffset = -248;
        int length = 0;

        try {
            buffer.read(targetBuffer, negativeOffset, length);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // The buffer rejects the negative offset: "Illegal offset: -248"
            verifyException("org.apache.commons.io.input.buffer.CircularByteBuffer", e);
        }
    }
}
