package org.apache.commons.io.input.buffer;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CircularByteBuffer_ESTest_test00 extends CircularByteBuffer_ESTest_scaffolding {

    /**
     * Reading zero bytes from a freshly created buffer is a no-op: it removes
     * nothing, so the available space stays equal to the buffer's capacity.
     */
    @Test(timeout = 4000)
    public void readZeroBytesLeavesSpaceUnchanged() throws Throwable {
        final int capacity = 260;
        CircularByteBuffer buffer = new CircularByteBuffer(capacity);

        byte[] target = new byte[1];
        buffer.read(target, 0, 0);

        assertEquals(capacity, buffer.getSpace());
    }
}
