package org.apache.commons.io.input.buffer;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CircularByteBuffer_ESTest_test23 extends CircularByteBuffer_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test_add_withNegativeLength_throwsIllegalArgumentException() throws Throwable {
        // Adding bytes with a negative length should be rejected immediately.
        byte[] sourceBytes = new byte[16];
        CircularByteBuffer buffer = new CircularByteBuffer();
        int offset = 8;      // byte value 8, cast from (byte) 8
        int negativeLength = -1907;

        try {
            buffer.add(sourceBytes, offset, negativeLength);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            verifyException("org.apache.commons.io.input.buffer.CircularByteBuffer", e);
        }
    }
}
