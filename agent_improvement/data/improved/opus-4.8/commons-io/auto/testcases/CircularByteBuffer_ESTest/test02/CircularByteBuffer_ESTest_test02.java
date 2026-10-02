package org.apache.commons.io.input.buffer;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CircularByteBuffer_ESTest_test02 extends CircularByteBuffer_ESTest_scaffolding {

    /**
     * Verifies that {@link CircularByteBuffer#read(byte[], int, int)} rejects a
     * requested length that exceeds the buffer's capacity. The buffer here holds
     * at most 2 bytes, so asking to read 56 bytes must fail with an
     * IllegalArgumentException ("Illegal length: 56").
     */
    @Test(timeout = 4000)
    public void readWithLengthLargerThanCapacityThrowsIllegalArgumentException() throws Throwable {
        int bufferCapacity = 2;
        CircularByteBuffer buffer = new CircularByteBuffer(bufferCapacity);

        byte[] target = new byte[6];
        int targetOffset = 2;
        int lengthExceedingCapacity = 56;

        try {
            buffer.read(target, targetOffset, lengthExceedingCapacity);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // Illegal length: 56
            verifyException("org.apache.commons.io.input.buffer.CircularByteBuffer", e);
        }
    }
}
