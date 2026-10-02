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
     * Verifies that add(byte[], offset, length) rejects a negative offset by
     * throwing an IllegalArgumentException, because the offset is validated
     * (offset < 0) before any byte is copied.
     */
    @Test(timeout = 4000)
    public void addWithNegativeOffsetThrowsIllegalArgumentException() throws Throwable {
        CircularByteBuffer buffer = new CircularByteBuffer(1);
        byte[] source = new byte[3];
        int negativeOffset = -2153;
        int length = 4;

        try {
            buffer.add(source, negativeOffset, length);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // Message reads: "Illegal offset: -2153"
            verifyException("org.apache.commons.io.input.buffer.CircularByteBuffer", e);
        }
    }
}
