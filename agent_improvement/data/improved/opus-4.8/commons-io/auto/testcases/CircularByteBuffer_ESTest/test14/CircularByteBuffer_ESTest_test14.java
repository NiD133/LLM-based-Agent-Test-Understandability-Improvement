package org.apache.commons.io.input.buffer;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CircularByteBuffer_ESTest_test14 extends CircularByteBuffer_ESTest_scaffolding {

    /**
     * Peeking on an empty buffer with a length greater than the number of buffered
     * bytes (here: 2 vs. 0) returns true, because there is nothing to compare against.
     * The peek does not consume anything, so the full default capacity (8192 bytes)
     * remains available.
     */
    @Test(timeout = 4000)
    public void peekOnEmptyBufferReturnsTrueAndLeavesSpaceUnchanged() throws Throwable {
        CircularByteBuffer buffer = new CircularByteBuffer();

        byte[] comparisonBytes = new byte[6];
        boolean nextBytesMatch = buffer.peek(comparisonBytes, 0, 2);

        assertTrue(nextBytesMatch);
        assertEquals(8192, buffer.getSpace());
    }
}
