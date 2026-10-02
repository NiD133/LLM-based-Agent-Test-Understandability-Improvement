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
    public void test06_readWithNegativeOffsetThrowsIllegalArgumentException() throws Throwable {
        CircularByteBuffer buffer = new CircularByteBuffer(1);
        byte[] destination = new byte[8];
        int negativeOffset = -248;
        int length = 0;

        // read() must reject a negative targetOffset with IllegalArgumentException
        try {
            buffer.read(destination, negativeOffset, length);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected message: "Illegal offset: -248"
            verifyException("org.apache.commons.io.input.buffer.CircularByteBuffer", e);
        }
    }
}
