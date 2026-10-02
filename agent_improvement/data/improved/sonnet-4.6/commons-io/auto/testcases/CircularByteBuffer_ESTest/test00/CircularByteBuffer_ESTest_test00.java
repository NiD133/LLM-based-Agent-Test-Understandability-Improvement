package org.apache.commons.io.input.buffer;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CircularByteBuffer_ESTest_test00 extends CircularByteBuffer_ESTest_scaffolding {

    // Verifies that a zero-length read does not consume any buffer space
    @Test(timeout = 4000)
    public void test00() throws Throwable {
        final int bufferCapacity = 260;
        CircularByteBuffer buffer = new CircularByteBuffer(bufferCapacity);

        byte[] outputBuffer = new byte[1];
        // Read zero bytes — this is a no-op and should not change the available space
        buffer.read(outputBuffer, 0, 0);

        assertEquals(bufferCapacity, buffer.getSpace());
    }
}
