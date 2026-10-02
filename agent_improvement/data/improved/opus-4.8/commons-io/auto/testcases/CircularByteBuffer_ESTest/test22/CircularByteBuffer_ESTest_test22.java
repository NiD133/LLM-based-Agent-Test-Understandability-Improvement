package org.apache.commons.io.input.buffer;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CircularByteBuffer_ESTest_test22 extends CircularByteBuffer_ESTest_scaffolding {

    /**
     * Verifies that {@link CircularByteBuffer#add(byte[], int, int)} rejects a request
     * to add more bytes than the buffer can hold by throwing an IllegalStateException.
     * Here the buffer has a capacity of 8, but the call asks to add 1783 bytes, which
     * does not fit, so "No space available" is reported.
     */
    @Test(timeout = 4000)
    public void addMoreBytesThanCapacityThrowsIllegalStateException() throws Throwable {
        final int bufferCapacity = 8;
        CircularByteBuffer buffer = new CircularByteBuffer(bufferCapacity);

        byte[] source = new byte[16];
        int sourceOffset = 8;
        int lengthExceedingCapacity = 1783;

        try {
            buffer.add(source, sourceOffset, lengthExceedingCapacity);
            fail("Expecting exception: IllegalStateException");
        } catch (IllegalStateException e) {
            // The buffer cannot accommodate 1783 bytes, so it rejects the request.
            verifyException("org.apache.commons.io.input.buffer.CircularByteBuffer", e);
        }
    }
}
