package org.apache.commons.io.input.buffer;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CircularByteBuffer_ESTest_test15 extends CircularByteBuffer_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test15() throws Throwable {
        CircularByteBuffer buffer = new CircularByteBuffer(260);
        byte[] sourceBuffer = new byte[1];
        int negativeOffset = -2988;

        try {
            buffer.peek(sourceBuffer, negativeOffset, (byte) 53);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // peek() must reject a negative offset with "Illegal offset: -2988"
            verifyException("org.apache.commons.io.input.buffer.CircularByteBuffer", e);
        }
    }
}
