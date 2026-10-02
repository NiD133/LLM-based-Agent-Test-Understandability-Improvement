package org.apache.commons.io.input.buffer;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CircularByteBuffer_ESTest_test29 extends CircularByteBuffer_ESTest_scaffolding {

    /** The default buffer capacity (IOUtils.DEFAULT_BUFFER_SIZE) used by the no-arg constructor. */
    private static final int DEFAULT_BUFFER_SIZE = 8192;

    /**
     * Clearing an already-empty buffer leaves it empty, so all of its capacity
     * remains available as free space.
     */
    @Test(timeout = 4000)
    public void clearEmptyBufferKeepsFullCapacityAsSpace() throws Throwable {
        CircularByteBuffer buffer = new CircularByteBuffer();

        buffer.clear();

        assertEquals(DEFAULT_BUFFER_SIZE, buffer.getSpace());
    }
}
