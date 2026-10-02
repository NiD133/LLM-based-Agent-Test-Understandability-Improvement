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

    @Test(timeout = 4000)
    public void test01() throws Throwable {
        CircularByteBuffer buffer = new CircularByteBuffer();
        // A 3-byte array: offset=2, length=3 → requires indices 2..4, but array only has indices 0..2
        byte[] threeByteArray = new byte[3];
        try {
            buffer.read(threeByteArray, 2, 3);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // offset(2) + length(3) = 5 > array size(3), so the buffer correctly rejects the read
            verifyException("org.apache.commons.io.input.buffer.CircularByteBuffer", e);
        }
    }
}
