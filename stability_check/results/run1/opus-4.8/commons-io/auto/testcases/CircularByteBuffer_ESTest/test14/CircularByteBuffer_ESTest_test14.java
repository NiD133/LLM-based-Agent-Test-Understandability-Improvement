package org.apache.commons.io.input.buffer;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CircularByteBuffer_ESTest_test14 extends CircularByteBuffer_ESTest_scaffolding {

    /**
     * Peeking into an empty buffer with an all-zero comparison array should
     * succeed: the buffer holds no bytes, so the requested bytes (which are all
     * zero, matching the freshly allocated internal storage) are considered a
     * match. The peek must not consume anything, so the full default capacity
     * (8192 bytes) remains available afterwards.
     */
    @Test(timeout = 4000)
    public void peekOnEmptyBufferMatchesZerosAndLeavesSpaceUntouched() throws Throwable {
        CircularByteBuffer emptyBuffer = new CircularByteBuffer();

        byte[] comparisonBytes = new byte[6];
        int offset = 0;
        int length = 2;
        boolean matches = emptyBuffer.peek(comparisonBytes, offset, length);

        assertTrue(matches);
        assertEquals(8192, emptyBuffer.getSpace());
    }
}
