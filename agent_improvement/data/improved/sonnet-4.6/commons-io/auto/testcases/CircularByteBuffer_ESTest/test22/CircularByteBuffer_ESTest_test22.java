package org.apache.commons.io.input.buffer;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CircularByteBuffer_ESTest_test22 extends CircularByteBuffer_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void testAddThrowsWhenRequestedLengthExceedsBufferCapacity() throws Throwable {
        // Source array has 16 bytes; we start reading from offset 8
        byte[] sourceData = new byte[16];
        // Buffer capacity is only 8 bytes
        CircularByteBuffer buffer = new CircularByteBuffer(8);
        // Attempting to add 1783 bytes far exceeds the buffer's capacity of 8
        try {
            buffer.add(sourceData, 8, 1783);
            fail("Expecting exception: IllegalStateException");
        } catch (IllegalStateException e) {
            //
            // No space available
            //
            verifyException("org.apache.commons.io.input.buffer.CircularByteBuffer", e);
        }
    }
}
