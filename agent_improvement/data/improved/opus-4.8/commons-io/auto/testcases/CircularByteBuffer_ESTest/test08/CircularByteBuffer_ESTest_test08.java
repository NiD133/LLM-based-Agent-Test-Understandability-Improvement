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

    /**
     * Reading from a freshly constructed (and therefore empty) buffer must fail,
     * because there are no bytes available to return.
     */
    @Test(timeout = 4000)
    public void readFromEmptyBufferThrowsIllegalStateException() throws Throwable {
        CircularByteBuffer emptyBuffer = new CircularByteBuffer(8192);

        try {
            emptyBuffer.read();
            fail("Expecting exception: IllegalStateException");
        } catch (IllegalStateException e) {
            // read() throws "No bytes available." when the buffer holds no bytes.
            verifyException("org.apache.commons.io.input.buffer.CircularByteBuffer", e);
        }
    }
}
