package org.apache.commons.io.input.buffer;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CircularByteBuffer_ESTest_test28 extends CircularByteBuffer_ESTest_scaffolding {

    /**
     * A freshly constructed buffer (default size of 8192 bytes) should be empty:
     * it holds zero bytes and has its full capacity available as free space.
     */
    @Test(timeout = 4000)
    public void newBufferIsEmptyWithFullSpaceAvailable() throws Throwable {
        CircularByteBuffer buffer = new CircularByteBuffer();

        int currentNumberOfBytes = buffer.getCurrentNumberOfBytes();

        assertEquals("a new buffer should report its full default capacity as free space",
                8192, buffer.getSpace());
        assertEquals("a new buffer should contain no bytes", 0, currentNumberOfBytes);
    }
}
