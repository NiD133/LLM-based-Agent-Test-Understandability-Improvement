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
    public void test_read_throwsIllegalArgumentException_whenLengthExceedsBufferCapacity() throws Throwable {
        // Target array with room for 6 bytes; offset 2 leaves 4 bytes writable.
        byte[] targetBuffer = new byte[6];

        // Buffer with an internal capacity of only 2 bytes.
        CircularByteBuffer smallBuffer = new CircularByteBuffer(2);

        // Requesting 56 bytes exceeds the buffer's own capacity (2), so
        // read() must reject the call with an IllegalArgumentException.
        try {
            smallBuffer.read(targetBuffer, 2, 56);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            //
            // Illegal length: 56
            //
            verifyException("org.apache.commons.io.input.buffer.CircularByteBuffer", e);
        }
    }
}
