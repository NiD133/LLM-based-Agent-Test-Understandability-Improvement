package org.apache.commons.io.input.buffer;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CircularByteBuffer_ESTest_test07 extends CircularByteBuffer_ESTest_scaffolding {

    /**
     * Verifies that after consuming a byte (freeing a slot) and writing a new byte,
     * the circular buffer correctly reports data availability and returns the new byte.
     *
     * Sequence: add(-3) → read() discards it → add(33) → hasBytes() should be true
     * → read() should return 33, confirming circular wrap-around works correctly.
     */
    @Test(timeout = 4000)
    public void test07() throws Throwable {
        // Arrange: create a small 2-byte circular buffer
        CircularByteBuffer buffer = new CircularByteBuffer(2);

        // Fill one slot then drain it to advance the internal start/end offsets,
        // exercising the circular wrap-around logic
        buffer.add((byte) (-3));
        buffer.read(); // discard the placeholder byte; buffer is now empty but offsets have advanced

        // Act: write the actual value under test
        buffer.add((byte) 33);

        // Assert: the buffer holds the new byte and returns it correctly
        assertTrue("Buffer should report at least one byte available after add", buffer.hasBytes());
        byte readValue = buffer.read();
        assertEquals("read() should return the byte that was added after the wrap-around", (byte) 33, readValue);
    }
}
