package org.apache.commons.io.input.buffer;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CircularByteBuffer_ESTest_test27 extends CircularByteBuffer_ESTest_scaffolding {

    private static final int BUFFER_CAPACITY = 2;
    private static final byte FIRST_BYTE = (byte) (-14);
    private static final byte SECOND_BYTE = (byte) 0;

    @Test(timeout = 4000)
    public void test27() throws Throwable {
        CircularByteBuffer buffer = new CircularByteBuffer(BUFFER_CAPACITY);
        fillBuffer(buffer);

        try {
            buffer.add(FIRST_BYTE);
            fail("Expecting exception: IllegalStateException");
        } catch (IllegalStateException e) {
            verifyException("org.apache.commons.io.input.buffer.CircularByteBuffer", e);
        }
    }

    private void fillBuffer(CircularByteBuffer buffer) {
        buffer.add(FIRST_BYTE);
        buffer.add(SECOND_BYTE);
    }
}
