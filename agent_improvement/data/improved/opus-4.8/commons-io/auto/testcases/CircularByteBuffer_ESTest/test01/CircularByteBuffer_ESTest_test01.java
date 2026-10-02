package org.apache.commons.io.input.buffer;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CircularByteBuffer_ESTest_test01 extends CircularByteBuffer_ESTest_scaffolding {

    /**
     * Verifies that {@link CircularByteBuffer#read(byte[], int, int)} rejects a
     * request whose offset and length together exceed the target array's size.
     *
     * <p>Here the target array holds only 3 bytes, yet the read starts at
     * offset 2 and asks for 3 bytes, which would write past the end of the
     * array. The buffer must therefore throw an {@link IllegalArgumentException}.</p>
     */
    @Test(timeout = 4000)
    public void readWithOffsetPlusLengthBeyondTargetArrayThrowsIllegalArgumentException() throws Throwable {
        CircularByteBuffer buffer = new CircularByteBuffer();

        byte[] targetArray = new byte[3];
        int targetOffset = 2;
        int length = 3;

        try {
            buffer.read(targetArray, targetOffset, length);
            fail("Expected IllegalArgumentException: offset + length exceeds the target array length");
        } catch (IllegalArgumentException e) {
            // The supplied byte array contains only 3 bytes, but offset and length would require 4.
            verifyException("org.apache.commons.io.input.buffer.CircularByteBuffer", e);
        }
    }
}
