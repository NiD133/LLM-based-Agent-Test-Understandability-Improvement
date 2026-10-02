package org.apache.commons.io.input.buffer;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CircularByteBuffer_ESTest_test08 extends CircularByteBuffer_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test08() throws Throwable {
        CircularByteBuffer circularByteBuffer0 = new CircularByteBuffer(8192);
        // Undeclared exception!
        try {
            circularByteBuffer0.read();
            fail("Expecting exception: IllegalStateException");
        } catch (IllegalStateException e) {
            //
            // No bytes available.
            //
            verifyException("org.apache.commons.io.input.buffer.CircularByteBuffer", e);
        }
    }
}
