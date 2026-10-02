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

    // The default buffer size used by CircularByteBuffer() is IOUtils.DEFAULT_BUFFER_SIZE = 8192.
    private static final int DEFAULT_BUFFER_SIZE = 8192;

    /**
     * Verifies that peek() on an empty buffer returns true when the requested
     * comparison length exceeds the number of bytes currently stored, and that
     * the buffer's available space remains unchanged (no bytes are consumed).
     *
     * peek() semantics: returns true when length >= currentNumberOfBytes AND
     * each compared byte matches; an empty buffer trivially satisfies both.
     */
    @Test(timeout = 4000)
    public void test14_peekOnEmptyBufferReturnsTrueAndSpaceIsUnchanged() throws Throwable {
        CircularByteBuffer buffer = new CircularByteBuffer();

        // Source array of zeros; peek compares 2 bytes starting at offset 0
        byte[] sourceBytes = new byte[6];
        int peekOffset = 0;
        int peekLength = 2;

        boolean peekResult = buffer.peek(sourceBytes, peekOffset, peekLength);

        // An empty buffer has 0 bytes; peek with length=2 > 0 bytes returns true
        assertTrue(peekResult);
        // peek is non-destructive: available space must still equal the full default size
        assertEquals(DEFAULT_BUFFER_SIZE, buffer.getSpace());
    }
}
