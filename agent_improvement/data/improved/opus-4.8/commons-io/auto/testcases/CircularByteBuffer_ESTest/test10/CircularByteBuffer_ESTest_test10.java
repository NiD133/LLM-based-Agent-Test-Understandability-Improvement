package org.apache.commons.io.input.buffer;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CircularByteBuffer_ESTest_test10 extends CircularByteBuffer_ESTest_scaffolding {

    /**
     * Verifies that {@link CircularByteBuffer#peek(byte[], int, int)} returns false
     * when the requested peek length is smaller than the number of bytes currently
     * buffered, and that peeking leaves the buffer's free space unchanged.
     */
    @Test(timeout = 4000)
    public void peekShorterThanBufferedBytesReturnsFalseAndKeepsSpace() throws Throwable {
        // A default buffer holds IOUtils.DEFAULT_BUFFER_SIZE (8192) bytes.
        CircularByteBuffer buffer = new CircularByteBuffer();

        // Add 4 bytes in total: two separate 2-byte chunks taken from this source array.
        byte[] data = new byte[4];
        buffer.add(data, 2, 2);
        buffer.add(data, 2, 2);

        // peek length (2) is less than the 4 buffered bytes, so peek short-circuits to false.
        boolean peekResult = buffer.peek(data, 1, 2);
        assertFalse(peekResult);

        // peek does not consume bytes: 8192 - 4 buffered bytes = 8188 bytes of free space remain.
        assertEquals(8188, buffer.getSpace());
    }
}
