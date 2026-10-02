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
     * Verifies that peek() on a newly created, empty CircularByteBuffer returns true
     * when the comparison pattern is also zero-filled (both buffer and pattern are all zeros),
     * and that peek() does not consume any bytes (available space remains at default capacity).
     */
    @Test(timeout = 4000)
    public void test14() throws Throwable {
        // Default constructor allocates a buffer with IOUtils.DEFAULT_BUFFER_SIZE (8192) bytes
        CircularByteBuffer buffer = new CircularByteBuffer();

        // Pattern to peek for: 6 zero bytes (Java initialises byte arrays to zero)
        byte[] zeroPattern = new byte[6];

        // Peek at the first 2 bytes of the buffer starting at pattern offset 0.
        // An empty buffer filled with zeros matches a zero-filled pattern, so peek returns true.
        boolean peekMatched = buffer.peek(zeroPattern, (byte) 0, 2);

        assertTrue(peekMatched);
        // peek() is non-destructive; the full default capacity of 8192 bytes should still be available
        assertEquals(8192, buffer.getSpace());
    }
}
