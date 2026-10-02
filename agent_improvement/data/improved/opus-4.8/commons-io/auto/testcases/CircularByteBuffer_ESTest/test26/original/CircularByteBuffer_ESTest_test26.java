package org.apache.commons.io.input.buffer;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CircularByteBuffer_ESTest_test26 extends CircularByteBuffer_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test26() throws Throwable {
        CircularByteBuffer circularByteBuffer0 = new CircularByteBuffer(1);
        byte[] byteArray0 = new byte[3];
        // Undeclared exception!
        try {
            circularByteBuffer0.add(byteArray0, (-2153), (int) (byte) 4);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            //
            // Illegal offset: -2153
            //
            verifyException("org.apache.commons.io.input.buffer.CircularByteBuffer", e);
        }
    }
}
