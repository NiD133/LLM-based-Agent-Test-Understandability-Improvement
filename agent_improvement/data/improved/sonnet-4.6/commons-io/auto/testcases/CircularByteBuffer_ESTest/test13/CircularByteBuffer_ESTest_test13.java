package org.apache.commons.io.input.buffer;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CircularByteBuffer_ESTest_test13 extends CircularByteBuffer_ESTest_scaffolding {

    /**
     * Verifies that peek() throws IllegalArgumentException when the given offset
     * exceeds the length of the source buffer array.
     *
     * The source buffer has 6 bytes but offset 1498 is far beyond its valid range [0, 5],
     * so peek() must reject it immediately with "Illegal offset: 1498".
     */
    @Test(timeout = 4000)
    public void test13() throws Throwable {
        // Source buffer with 6 bytes (valid offsets: 0..5)
        byte[] sourceBuffer = new byte[6];

        // Buffer capacity of 2 bytes
        CircularByteBuffer circularByteBuffer0 = new CircularByteBuffer(2);

        // Offset 1498 is way out of bounds for a 6-element array — must throw
        try {
            circularByteBuffer0.peek(sourceBuffer, 1498, 1885);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            //
            // Illegal offset: 1498
            //
            verifyException("org.apache.commons.io.input.buffer.CircularByteBuffer", e);
        }
    }
}
