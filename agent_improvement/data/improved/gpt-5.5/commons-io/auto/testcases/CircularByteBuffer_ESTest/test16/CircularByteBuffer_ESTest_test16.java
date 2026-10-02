package org.apache.commons.io.input.buffer;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CircularByteBuffer_ESTest_test16 extends CircularByteBuffer_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test16() throws Throwable {
        final int bufferCapacity = 260;
        final int requestedBytes = 0;

        CircularByteBuffer emptyBuffer = new CircularByteBuffer(bufferCapacity);

        boolean hasEnoughSpaceForZeroBytes = emptyBuffer.hasSpace(requestedBytes);

        assertTrue(hasEnoughSpaceForZeroBytes);
        assertEquals(bufferCapacity, emptyBuffer.getSpace());
    }
}
