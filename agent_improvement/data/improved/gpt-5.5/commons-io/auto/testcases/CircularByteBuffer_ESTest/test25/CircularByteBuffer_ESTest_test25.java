package org.apache.commons.io.input.buffer;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CircularByteBuffer_ESTest_test25 extends CircularByteBuffer_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test25() throws Throwable {
        final CircularByteBuffer buffer = new CircularByteBuffer();
        final byte[] sourceBytes = new byte[6];
        final int sourceOffset = 2;
        final int numberOfBytesToAdd = 2;

        buffer.add(sourceBytes, sourceOffset, numberOfBytesToAdd);
        final boolean hasBufferedBytes = buffer.hasBytes();

        assertEquals(2, buffer.getCurrentNumberOfBytes());
        assertTrue(hasBufferedBytes);
    }
}
