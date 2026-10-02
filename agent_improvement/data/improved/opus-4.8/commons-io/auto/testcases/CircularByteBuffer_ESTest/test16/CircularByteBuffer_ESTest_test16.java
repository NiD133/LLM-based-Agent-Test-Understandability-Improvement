package org.apache.commons.io.input.buffer;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CircularByteBuffer_ESTest_test16 extends CircularByteBuffer_ESTest_scaffolding {

    /**
     * A freshly created buffer of a given capacity should report having room for
     * zero additional bytes and should expose its full capacity as free space.
     */
    @Test(timeout = 4000)
    public void hasSpaceForZeroBytesAndReportsFullCapacityWhenEmpty() throws Throwable {
        final int capacity = 260;
        CircularByteBuffer buffer = new CircularByteBuffer(capacity);

        boolean hasSpaceForZeroBytes = buffer.hasSpace(0);

        assertTrue("An empty buffer always has room for zero bytes", hasSpaceForZeroBytes);
        assertEquals("An empty buffer's free space equals its capacity", capacity, buffer.getSpace());
    }
}
