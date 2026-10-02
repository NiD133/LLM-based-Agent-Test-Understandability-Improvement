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
     * Verifies that peek() returns false when the buffer content does not match
     * the comparison array after a byte has been consumed via read().
     *
     * Scenario: a 2-byte capacity buffer receives one byte, that byte is read out,
     * then peek() checks whether the next 2 bytes match an all-zero array.
     * The remaining buffer content does not match, so peek() must return false.
     */
    @Test(timeout = 4000)
    public void test09() throws Throwable {
        // Comparison array filled with zeros (default byte value)
        byte[] comparisonArray = new byte[6];

        // Buffer with capacity for 2 bytes
        CircularByteBuffer buffer = new CircularByteBuffer(2);

        // Add a single byte so the buffer is non-empty
        buffer.add((byte) (-3));
        assertTrue(buffer.hasBytes());

        // Consume the byte, leaving the buffer empty
        buffer.read();

        // peek() checks if the next 2 bytes in the buffer match comparisonArray[0..1].
        // Because the byte stored in the underlying array (-3) does not equal
        // the zero values in comparisonArray, peek() returns false.
        boolean peekResult = buffer.peek(comparisonArray, (byte) 0, 2);
        assertFalse(peekResult);
    }
}
