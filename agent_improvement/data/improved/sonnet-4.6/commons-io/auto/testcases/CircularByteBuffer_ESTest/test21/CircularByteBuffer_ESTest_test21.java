package org.apache.commons.io.input.buffer;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CircularByteBuffer_ESTest_test21 extends CircularByteBuffer_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test_addThenRead_bufferBecomesEmpty() throws Throwable {
        // A 16-byte source array; we use the upper half (indices 8–15) as data
        byte[] sourceArray = new byte[16];

        // Create a buffer that holds exactly 8 bytes
        CircularByteBuffer buffer = new CircularByteBuffer(8);

        // Write 8 bytes from sourceArray starting at offset 8
        buffer.add(sourceArray, 8, 8);
        assertEquals(8, buffer.getCurrentNumberOfBytes());

        // Read those 8 bytes back into the same array at offset 8
        buffer.read(sourceArray, 8, 8);

        // After reading all bytes the buffer should report 8 bytes of free space
        assertEquals(8, buffer.getSpace());
    }
}
