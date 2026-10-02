package org.apache.commons.io.input.buffer;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CircularByteBuffer_ESTest_test03 extends CircularByteBuffer_ESTest_scaffolding {

    /**
     * Verifies that read() throws IllegalArgumentException when a negative length is supplied.
     * The buffer capacity and the target array are valid; only the length argument is illegal.
     */
    @Test(timeout = 4000)
    public void test03() throws Throwable {
        CircularByteBuffer buffer = new CircularByteBuffer(2);
        byte[] target = new byte[3];
        final int negativeLength = -14;

        try {
            buffer.read(target, 2, negativeLength);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            //
            // Illegal length: -14
            //
            verifyException("org.apache.commons.io.input.buffer.CircularByteBuffer", e);
        }
    }
}
