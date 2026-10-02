package org.apache.commons.io.input.buffer;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CircularByteBuffer_ESTest_test04 extends CircularByteBuffer_ESTest_scaffolding {

    /**
     * Verifies that {@link CircularByteBuffer#read(byte[], int, int)} rejects a target
     * offset that lies outside the bounds of the supplied byte array.
     *
     * <p>The target array is empty, so any non-negative offset (here 47) is already
     * past its end. The method must reject this with an {@link IllegalArgumentException}
     * reporting "Illegal offset: 47" before attempting to copy any bytes.</p>
     */
    @Test(timeout = 4000)
    public void readWithOffsetBeyondTargetArrayThrowsIllegalArgumentException() throws Throwable {
        CircularByteBuffer buffer = new CircularByteBuffer(47);
        byte[] emptyTarget = new byte[0];
        int offsetPastEnd = 47;
        int length = 47;

        try {
            buffer.read(emptyTarget, offsetPastEnd, length);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // Thrown because the offset (47) is not a valid index into the empty target array.
            // Message: "Illegal offset: 47"
            verifyException("org.apache.commons.io.input.buffer.CircularByteBuffer", e);
        }
    }
}
