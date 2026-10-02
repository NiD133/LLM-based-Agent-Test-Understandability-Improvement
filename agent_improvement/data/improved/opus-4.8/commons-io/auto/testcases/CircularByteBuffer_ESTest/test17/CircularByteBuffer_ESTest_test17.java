package org.apache.commons.io.input.buffer;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CircularByteBuffer_ESTest_test17 extends CircularByteBuffer_ESTest_scaffolding {

    /**
     * A buffer of capacity 8 cannot accommodate a request for 50 bytes, so
     * {@code hasSpace(50)} returns false. Since the buffer is still empty, all
     * 8 bytes of its capacity remain available via {@code getSpace()}.
     */
    @Test(timeout = 4000)
    public void hasSpaceReturnsFalseWhenRequestExceedsCapacity() throws Throwable {
        int bufferCapacity = 8;
        CircularByteBuffer buffer = new CircularByteBuffer(bufferCapacity);

        boolean canFit50Bytes = buffer.hasSpace(50);

        assertFalse(canFit50Bytes);
        assertEquals(bufferCapacity, buffer.getSpace());
    }
}
