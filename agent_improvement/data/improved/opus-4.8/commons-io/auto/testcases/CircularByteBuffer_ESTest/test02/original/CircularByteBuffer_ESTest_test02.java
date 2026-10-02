package org.apache.commons.io.input.buffer;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CircularByteBuffer_ESTest_test02 extends CircularByteBuffer_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test02() throws Throwable {
        byte[] byteArray0 = new byte[6];
        CircularByteBuffer circularByteBuffer0 = new CircularByteBuffer(2);
        // Undeclared exception!
        try {
            circularByteBuffer0.read(byteArray0, 2, 56);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            //
            // Illegal length: 56
            //
            verifyException("org.apache.commons.io.input.buffer.CircularByteBuffer", e);
        }
    }
}
