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

    private static final int BUFFER_CAPACITY = 1;
    private static final int SOURCE_LENGTH = 3;
    private static final int ILLEGAL_OFFSET = -2153;
    private static final int BYTES_TO_ADD = 4;

    @Test(timeout = 4000)
    public void test26() throws Throwable {
        CircularByteBuffer buffer = new CircularByteBuffer(BUFFER_CAPACITY);
        byte[] source = new byte[SOURCE_LENGTH];

        try {
            buffer.add(source, ILLEGAL_OFFSET, BYTES_TO_ADD);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            verifyException("org.apache.commons.io.input.buffer.CircularByteBuffer", e);
        }
    }
}
