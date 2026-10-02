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

    /**
     * Verifies that adding a byte to a completely full buffer throws
     * IllegalStateException with message "No space available".
     */
    @Test(timeout = 4000)
    public void testAddToFullBufferThrowsIllegalStateException() throws Throwable {
        // Create a buffer that holds exactly 2 bytes
        CircularByteBuffer circularByteBuffer0 = new CircularByteBuffer(2);

        // Fill the buffer to capacity
        circularByteBuffer0.add((byte) (-14));
        circularByteBuffer0.add((byte) 0);

        // Attempting to add a third byte must throw because no space remains
        try {
            circularByteBuffer0.add((byte) (-14));
            fail("Expecting exception: IllegalStateException");
        } catch (IllegalStateException e) {
            //
            // No space available
            //
            verifyException("org.apache.commons.io.input.buffer.CircularByteBuffer", e);
        }
    }
}
