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
     * Verifies that bytes added after an earlier read are stored and returned
     * correctly, exercising the circular (wrap-around) behaviour of the buffer.
     *
     * Scenario on a buffer with capacity 2:
     *   1. Add a first byte, then read it back so the buffer is empty again
     *      but its internal read/write offsets have advanced.
     *   2. Add a second byte; the buffer must now report that it holds data.
     *   3. Read that second byte and confirm it matches what was added.
     */
    @Test(timeout = 4000)
    public void test07() throws Throwable {
        final byte firstByte = (byte) -3;
        final byte secondByte = (byte) 33;

        CircularByteBuffer buffer = new CircularByteBuffer(2);

        // Add and immediately consume the first byte to advance the offsets.
        buffer.add(firstByte);
        buffer.read();

        // Add a second byte; the buffer should now hold exactly that byte.
        buffer.add(secondByte);
        assertTrue(buffer.hasBytes());

        // Reading it back must return the byte that was just added.
        byte readByte = buffer.read();
        assertEquals(secondByte, readByte);
    }
}
