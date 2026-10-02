package org.apache.commons.io.input.buffer;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CircularByteBuffer_ESTest_test06 extends CircularByteBuffer_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test06() throws Throwable {
        CircularByteBuffer oneByteBuffer = new CircularByteBuffer(1);
        byte[] destination = new byte[8];

        try {
            oneByteBuffer.read(destination, (-248), (int) (byte) 0);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            verifyException("org.apache.commons.io.input.buffer.CircularByteBuffer", e);
        }
    }
}
