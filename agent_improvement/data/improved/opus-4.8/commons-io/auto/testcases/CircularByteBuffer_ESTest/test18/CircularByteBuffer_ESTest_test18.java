package org.apache.commons.io.input.buffer;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CircularByteBuffer_ESTest_test18 extends CircularByteBuffer_ESTest_scaffolding {

    /** Default buffer capacity (IOUtils.DEFAULT_BUFFER_SIZE) used by the no-arg constructor. */
    private static final int DEFAULT_BUFFER_SIZE = 8192;

    /**
     * A freshly created buffer is empty, so it has room for new bytes:
     * hasSpace() returns true and getSpace() reports the full default capacity.
     */
    @Test(timeout = 4000)
    public void newBufferHasFullCapacityAvailable() throws Throwable {
        CircularByteBuffer emptyBuffer = new CircularByteBuffer();

        boolean hasSpace = emptyBuffer.hasSpace();

        assertTrue("A new, empty buffer should have space for more bytes", hasSpace);
        assertEquals("A new buffer should report its full default capacity as free space",
                DEFAULT_BUFFER_SIZE, emptyBuffer.getSpace());
    }
}
