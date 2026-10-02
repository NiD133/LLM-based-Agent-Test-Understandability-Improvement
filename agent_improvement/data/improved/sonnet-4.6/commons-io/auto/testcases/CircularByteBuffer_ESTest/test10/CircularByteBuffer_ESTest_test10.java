package org.apache.commons.io.input.buffer;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CircularByteBuffer_ESTest_test10 extends CircularByteBuffer_ESTest_scaffolding {

    /**
     * Verifies that peek() returns false when the requested comparison length
     * is less than the number of bytes currently held in the buffer.
     *
     * The buffer is loaded with 4 bytes (two add() calls of 2 bytes each).
     * peek() is then called asking to compare only 2 bytes, which is less
     * than the 4 bytes in the buffer, so it must return false.
     * The default buffer capacity is 8192, so remaining space is 8192 - 4 = 8188.
     */
    @Test(timeout = 4000)
    public void test10() throws Throwable {
        CircularByteBuffer buffer = new CircularByteBuffer();
        byte[] data = new byte[4];

        // Fill the buffer with 4 zero bytes via two separate add() calls
        buffer.add(data, 2, 2);
        buffer.add(data, 2, 2);

        // peek() with length (2) < currentNumberOfBytes (4) returns false
        boolean peekResult = buffer.peek(data, 1, 2);

        assertEquals(8188, buffer.getSpace());
        assertFalse(peekResult);
    }
}
