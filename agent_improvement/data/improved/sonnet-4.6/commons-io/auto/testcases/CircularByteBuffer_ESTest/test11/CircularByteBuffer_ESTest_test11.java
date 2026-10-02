package org.apache.commons.io.input.buffer;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CircularByteBuffer_ESTest_test11 extends CircularByteBuffer_ESTest_scaffolding {

    /**
     * Verifies that peek() throws IllegalArgumentException when the requested
     * length exceeds the buffer's capacity. The buffer holds 7 bytes, but peek
     * is asked to compare 27 bytes — more than the buffer can ever contain.
     */
    @Test(timeout = 4000)
    public void test11() throws Throwable {
        byte[] sourceArray = new byte[16];
        int bufferCapacity = (byte) 7;  // buffer capacity of 7 bytes
        CircularByteBuffer circularByteBuffer = new CircularByteBuffer(bufferCapacity);

        int offset = (byte) 7;
        int lengthExceedingCapacity = 27; // 27 > bufferCapacity (7), so peek must reject it

        // Undeclared exception!
        try {
            circularByteBuffer.peek(sourceArray, offset, lengthExceedingCapacity);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            //
            // Illegal length: 27
            //
            verifyException("org.apache.commons.io.input.buffer.CircularByteBuffer", e);
        }
    }
}
