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

    @Test(timeout = 4000)
    public void test11() throws Throwable {
        byte[] byteArray0 = new byte[16];
        CircularByteBuffer circularByteBuffer0 = new CircularByteBuffer((byte) 7);
        // Undeclared exception!
        try {
            circularByteBuffer0.peek(byteArray0, (byte) 7, 27);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            //
            // Illegal length: 27
            //
            verifyException("org.apache.commons.io.input.buffer.CircularByteBuffer", e);
        }
    }
}
