package org.apache.commons.io.input.buffer;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CircularByteBuffer_ESTest_test21 extends CircularByteBuffer_ESTest_scaffolding {

    /**
     * Verifies that bytes added to the buffer can be read back, and that the
     * buffer's byte count and free space are tracked correctly across both
     * operations.
     */
    @Test(timeout = 4000)
    public void testAddThenReadUpdatesByteCountAndSpace() throws Throwable {
        final int bufferCapacity = 8;
        final int transferLength = 8;
        final int arrayOffset = 8;

        byte[] data = new byte[16];
        CircularByteBuffer buffer = new CircularByteBuffer(bufferCapacity);

        // Add 8 bytes (from index 8..15 of the array) into the buffer, filling it.
        buffer.add(data, arrayOffset, transferLength);
        assertEquals(8, buffer.getCurrentNumberOfBytes());

        // Read those 8 bytes back out, which frees the whole buffer again.
        buffer.read(data, arrayOffset, transferLength);
        assertEquals(8, buffer.getSpace());
    }
}
