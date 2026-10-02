package org.apache.commons.io.input.buffer;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CircularByteBuffer_ESTest_test25 extends CircularByteBuffer_ESTest_scaffolding {

    /**
     * Verifies that after adding bytes to a CircularByteBuffer, hasBytes() returns true
     * and getCurrentNumberOfBytes() reflects the exact number of bytes added.
     *
     * The buffer is populated by adding 2 bytes from a source array (starting at offset 2),
     * then we confirm the buffer reports it has content and the correct byte count.
     */
    @Test(timeout = 4000)
    public void test25_hasBytes_returnsTrueAndByteCountIsCorrectAfterAdd() throws Throwable {
        CircularByteBuffer buffer = new CircularByteBuffer();
        byte[] sourceData = new byte[6];
        int sourceOffset = 2;
        int byteCountToAdd = 2;

        buffer.add(sourceData, sourceOffset, byteCountToAdd);

        boolean bufferHasContent = buffer.hasBytes();
        assertEquals("Buffer should contain exactly the number of bytes that were added",
                byteCountToAdd, buffer.getCurrentNumberOfBytes());
        assertTrue("Buffer should report it has bytes after add", bufferHasContent);
    }
}
