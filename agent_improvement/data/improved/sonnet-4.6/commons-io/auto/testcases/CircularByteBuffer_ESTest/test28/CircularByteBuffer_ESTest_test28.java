package org.apache.commons.io.input.buffer;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CircularByteBuffer_ESTest_test28 extends CircularByteBuffer_ESTest_scaffolding {

    // The default buffer capacity used when no size is specified (matches IOUtils.DEFAULT_BUFFER_SIZE)
    private static final int DEFAULT_BUFFER_CAPACITY = 8192;

    @Test(timeout = 4000)
    public void test28_newBufferIsEmptyAndHasFullDefaultCapacity() throws Throwable {
        CircularByteBuffer buffer = new CircularByteBuffer();

        int bytesInBuffer = buffer.getCurrentNumberOfBytes();

        // A freshly created buffer should contain no bytes
        assertEquals(0, bytesInBuffer);
        // All capacity should be available as space
        assertEquals(DEFAULT_BUFFER_CAPACITY, buffer.getSpace());
    }
}
