package org.apache.commons.io.input.buffer;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CircularByteBuffer_ESTest_test12 extends CircularByteBuffer_ESTest_scaffolding {

    /**
     * Verifies that peek() throws IllegalArgumentException when given a negative length.
     * The buffer's validation must reject negative lengths regardless of buffer content.
     */
    @Test(timeout = 4000)
    public void test12_peek_throwsOnNegativeLength() throws Throwable {
        CircularByteBuffer buffer = new CircularByteBuffer();
        byte[] sourceArray = new byte[3];
        int negativeLength = -2186;

        try {
            buffer.peek(sourceArray, 0, negativeLength);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            //
            // Illegal length: -2186
            //
            verifyException("org.apache.commons.io.input.buffer.CircularByteBuffer", e);
        }
    }
}
