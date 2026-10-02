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

    // Default buffer capacity is IOUtils.DEFAULT_BUFFER_SIZE = 8192 bytes
    private static final int DEFAULT_BUFFER_CAPACITY = 8192;

    @Test(timeout = 4000)
    public void test14_peekOnEmptyBufferWithZeroFilledArrayReturnsTrue() throws Throwable {
        // An empty buffer is initialized with all-zero internal bytes
        CircularByteBuffer emptyBuffer = new CircularByteBuffer();

        // A zero-filled source array to compare against the empty buffer's contents
        byte[] zeroFilledSource = new byte[6];

        // peek at offset 0, length 2: compares 2 bytes of zeroFilledSource against the
        // empty buffer's internal bytes (also zero). Since length(2) >= currentBytes(0),
        // peek does not short-circuit and all compared bytes match, so it returns true.
        boolean peekMatched = emptyBuffer.peek(zeroFilledSource, (byte) 0, 2);

        assertTrue(peekMatched);

        // peek is non-destructive: the full default capacity must still be available
        assertEquals(DEFAULT_BUFFER_CAPACITY, emptyBuffer.getSpace());
    }
}
