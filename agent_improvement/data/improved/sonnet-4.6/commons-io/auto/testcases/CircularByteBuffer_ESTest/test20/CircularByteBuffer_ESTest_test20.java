package org.apache.commons.io.input.buffer;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CircularByteBuffer_ESTest_test20 extends CircularByteBuffer_ESTest_scaffolding {

    // A newly created buffer holds no bytes and all capacity is available as free space.
    @Test(timeout = 4000)
    public void test_newBufferHasNoBytesAndFullSpaceAvailable() throws Throwable {
        final int bufferCapacity = 260;
        CircularByteBuffer buffer = new CircularByteBuffer(bufferCapacity);

        boolean hasBytes = buffer.hasBytes();
        assertFalse("Newly created buffer should contain no bytes", hasBytes);
        assertEquals("Available space should equal the full capacity for an empty buffer",
                bufferCapacity, buffer.getSpace());
    }
}
