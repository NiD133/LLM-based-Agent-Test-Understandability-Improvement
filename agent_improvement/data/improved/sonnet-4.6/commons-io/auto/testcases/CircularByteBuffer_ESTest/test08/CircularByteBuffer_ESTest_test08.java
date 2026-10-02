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
     * Verifies that reading from an empty CircularByteBuffer throws IllegalStateException.
     * A freshly created buffer contains no bytes, so any attempt to read should fail
     * with "No bytes available." rather than return garbage data.
     */
    @Test(timeout = 4000)
    public void test08_readFromEmptyBuffer_throwsIllegalStateException() throws Throwable {
        CircularByteBuffer emptyBuffer = new CircularByteBuffer(8192);

        try {
            emptyBuffer.read();
            fail("Expecting exception: IllegalStateException");
        } catch (IllegalStateException e) {
            verifyException("org.apache.commons.io.input.buffer.CircularByteBuffer", e);
        }
    }
}
