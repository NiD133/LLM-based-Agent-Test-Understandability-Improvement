package org.apache.commons.io.input.buffer;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CircularByteBuffer_ESTest_test23 extends CircularByteBuffer_ESTest_scaffolding {

    /**
     * Verifies that {@link CircularByteBuffer#add(byte[], int, int)} rejects a
     * negative length by throwing an {@link IllegalArgumentException}, even when
     * the source array and offset are otherwise valid.
     */
    @Test(timeout = 4000)
    public void addWithNegativeLengthThrowsIllegalArgumentException() throws Throwable {
        byte[] sourceBytes = new byte[16];
        int validOffset = 8;
        int negativeLength = -1907;
        CircularByteBuffer buffer = new CircularByteBuffer();

        try {
            buffer.add(sourceBytes, validOffset, negativeLength);
            fail("Expecting exception: IllegalArgumentException for negative length");
        } catch (IllegalArgumentException e) {
            // The CUT reports the offending value: "Illegal length: -1907"
            verifyException("org.apache.commons.io.input.buffer.CircularByteBuffer", e);
        }
    }
}
