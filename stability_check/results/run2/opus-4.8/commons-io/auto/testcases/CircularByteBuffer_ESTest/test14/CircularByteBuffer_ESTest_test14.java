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
     * Peeking into a freshly created (empty) buffer succeeds because the
     * requested length (2) is not smaller than the number of buffered bytes (0),
     * and peeking does not consume anything, so the full default capacity
     * ({@code IOUtils.DEFAULT_BUFFER_SIZE} = 8192) remains available.
     */
    @Test(timeout = 4000)
    public void peekOnEmptyBufferReturnsTrueAndLeavesFullSpace() throws Throwable {
        CircularByteBuffer emptyBuffer = new CircularByteBuffer();

        byte[] comparisonBytes = new byte[6];
        int offset = 0;
        int lengthToCompare = 2;
        boolean peekMatched = emptyBuffer.peek(comparisonBytes, offset, lengthToCompare);

        assertTrue(peekMatched);
        assertEquals(8192, emptyBuffer.getSpace());
    }
}
