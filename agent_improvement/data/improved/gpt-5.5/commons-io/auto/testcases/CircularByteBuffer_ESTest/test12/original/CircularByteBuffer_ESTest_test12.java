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

    @Test(timeout = 4000)
    public void test12() throws Throwable {
        CircularByteBuffer circularByteBuffer0 = new CircularByteBuffer();
        byte[] byteArray0 = new byte[3];
        // Undeclared exception!
        try {
            circularByteBuffer0.peek(byteArray0, (byte) 0, (-2186));
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            //
            // Illegal length: -2186
            //
            verifyException("org.apache.commons.io.input.buffer.CircularByteBuffer", e);
        }
    }
}
