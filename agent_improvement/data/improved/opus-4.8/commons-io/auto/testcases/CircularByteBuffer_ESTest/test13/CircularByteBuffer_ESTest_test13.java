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
     * Verifies that {@link CircularByteBuffer#peek(byte[], int, int)} rejects an
     * offset that lies outside the bounds of the supplied source array.
     * The 6-element array only has valid offsets 0..5, so an offset of 1498
     * must trigger an IllegalArgumentException ("Illegal offset: 1498").
     */
    @Test(timeout = 4000)
    public void peekWithOffsetBeyondArrayLengthThrowsIllegalArgumentException() throws Throwable {
        byte[] sourceBuffer = new byte[6];
        CircularByteBuffer buffer = new CircularByteBuffer(2);

        int offsetBeyondArrayLength = 1498;
        int length = 1885;

        try {
            buffer.peek(sourceBuffer, offsetBeyondArrayLength, length);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // Illegal offset: 1498
            verifyException("org.apache.commons.io.input.buffer.CircularByteBuffer", e);
        }
    }
}
