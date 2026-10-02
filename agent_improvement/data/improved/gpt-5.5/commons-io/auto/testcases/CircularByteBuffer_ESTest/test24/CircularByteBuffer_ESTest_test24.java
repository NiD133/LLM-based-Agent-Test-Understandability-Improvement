package org.apache.commons.io.input.buffer;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CircularByteBuffer_ESTest_test24 extends CircularByteBuffer_ESTest_scaffolding {

    private static final int SOURCE_BUFFER_LENGTH = 16;
    private static final int OFFSET_PAST_END_OF_SOURCE = 28;
    private static final int BYTES_TO_ADD = 28;

    @Test(timeout = 4000)
    public void test24() throws Throwable {
        byte[] sourceBuffer = new byte[SOURCE_BUFFER_LENGTH];
        CircularByteBuffer circularByteBuffer = new CircularByteBuffer();

        try {
            circularByteBuffer.add(sourceBuffer, OFFSET_PAST_END_OF_SOURCE, BYTES_TO_ADD);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            //
            // Illegal offset: 28
            //
            verifyException("org.apache.commons.io.input.buffer.CircularByteBuffer", e);
        }
    }
}
