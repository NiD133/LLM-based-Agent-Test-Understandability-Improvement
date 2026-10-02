package org.apache.commons.io.input.buffer;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CircularByteBuffer_ESTest_test24 extends CircularByteBuffer_ESTest_scaffolding {

    /**
     * add(byte[], offset, length) must reject an offset that lies outside the
     * source array. Here the source array holds 16 bytes, so the offset 28 is
     * out of bounds and an IllegalArgumentException ("Illegal offset: 28") is
     * expected.
     */
    @Test(timeout = 4000)
    public void addWithOffsetBeyondSourceArrayThrowsIllegalArgumentException() throws Throwable {
        byte[] sourceBuffer = new byte[16];
        int outOfBoundsOffset = 28;
        int length = 28;
        CircularByteBuffer buffer = new CircularByteBuffer();

        try {
            buffer.add(sourceBuffer, outOfBoundsOffset, length);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // Illegal offset: 28
            verifyException("org.apache.commons.io.input.buffer.CircularByteBuffer", e);
        }
    }
}
