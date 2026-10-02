package org.apache.commons.io.input.buffer;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CircularByteBuffer_ESTest_test09 extends CircularByteBuffer_ESTest_scaffolding {

    /**
     * After the only buffered byte has been read back out, the buffer is empty,
     * so peeking for two bytes against an all-zero comparison array must fail.
     */
    @Test(timeout = 4000)
    public void peekFailsAfterBufferedByteIsConsumed() throws Throwable {
        CircularByteBuffer buffer = new CircularByteBuffer(2);

        buffer.add((byte) -3);
        assertTrue("Buffer should hold the byte that was just added", buffer.hasBytes());

        // Consume the single byte, leaving the buffer empty.
        buffer.read();

        byte[] comparison = new byte[6];
        boolean nextBytesMatch = buffer.peek(comparison, 0, 2);

        assertFalse("Empty buffer cannot match a two-byte peek", nextBytesMatch);
    }
}
