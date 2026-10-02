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
    public void test23() throws Throwable {
        byte[] source = new byte[16];
        CircularByteBuffer buffer = new CircularByteBuffer();
        int validOffset = (int) (byte) 8;
        int invalidLength = (-1907);

        try {
            buffer.add(source, validOffset, invalidLength);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            verifyException("org.apache.commons.io.input.buffer.CircularByteBuffer", e);
        }
    }
}
