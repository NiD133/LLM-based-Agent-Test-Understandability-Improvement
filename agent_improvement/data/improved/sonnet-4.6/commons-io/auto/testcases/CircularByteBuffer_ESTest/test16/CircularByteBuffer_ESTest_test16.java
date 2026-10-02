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

    private static final int BUFFER_CAPACITY = 260;

    @Test(timeout = 4000)
    public void test_hasSpaceForZeroBytes_returnsTrueAndSpaceEqualsCapacityOnEmptyBuffer() throws Throwable {
        CircularByteBuffer emptyBuffer = new CircularByteBuffer(BUFFER_CAPACITY);

        // Asking for 0 bytes of space on a freshly created buffer should always succeed
        boolean hasSpaceForZeroBytes = emptyBuffer.hasSpace(0);
        assertTrue(hasSpaceForZeroBytes);

        // With nothing added yet, available space must equal the full buffer capacity
        assertEquals(BUFFER_CAPACITY, emptyBuffer.getSpace());
    }
}
