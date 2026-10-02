package org.apache.commons.io.input.buffer;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CircularByteBuffer_ESTest_test17 extends CircularByteBuffer_ESTest_scaffolding {

    /**
     * Verifies that hasSpace(count) returns false when the requested count exceeds the
     * buffer's total capacity, and that getSpace() still reflects the full available
     * space of an empty buffer.
     */
    @Test(timeout = 4000)
    public void test_hasSpace_returnsFalse_whenRequestedCountExceedsBufferCapacity() throws Throwable {
        final int bufferCapacity = 8;
        CircularByteBuffer buffer = new CircularByteBuffer((byte) bufferCapacity);

        // Requesting space for 50 bytes in an 8-byte buffer must return false
        boolean hasSpaceFor50Bytes = buffer.hasSpace(50);
        assertFalse(hasSpaceFor50Bytes);

        // No bytes have been added, so all 8 bytes of capacity are still free
        assertEquals(bufferCapacity, buffer.getSpace());
    }
}
