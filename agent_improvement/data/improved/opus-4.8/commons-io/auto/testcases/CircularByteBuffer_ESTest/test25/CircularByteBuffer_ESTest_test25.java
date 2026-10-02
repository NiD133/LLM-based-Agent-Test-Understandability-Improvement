package org.apache.commons.io.input.buffer;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CircularByteBuffer_ESTest_test25 extends CircularByteBuffer_ESTest_scaffolding {

    /**
     * Verifies that adding a sub-range of a byte array updates the buffer's
     * byte count and reports that bytes are present.
     */
    @Test(timeout = 4000)
    public void addSubRangeUpdatesCountAndHasBytes() throws Throwable {
        CircularByteBuffer buffer = new CircularByteBuffer();

        // Add 2 bytes (starting at offset 2) from a 6-byte source array.
        byte[] source = new byte[6];
        buffer.add(source, 2, 2);

        boolean hasBytes = buffer.hasBytes();

        assertEquals("buffer should hold the 2 bytes that were added",
                2, buffer.getCurrentNumberOfBytes());
        assertTrue("buffer should report holding bytes after an add", hasBytes);
    }
}
