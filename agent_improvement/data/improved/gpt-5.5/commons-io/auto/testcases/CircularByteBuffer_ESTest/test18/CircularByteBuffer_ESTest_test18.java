package org.apache.commons.io.input.buffer;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CircularByteBuffer_ESTest_test18 extends CircularByteBuffer_ESTest_scaffolding {

    private static final int DEFAULT_BUFFER_CAPACITY = 8192;

    @Test(timeout = 4000)
    public void test18() throws Throwable {
        CircularByteBuffer emptyDefaultBuffer = new CircularByteBuffer();

        boolean hasSpaceForAtLeastOneByte = emptyDefaultBuffer.hasSpace();
        int availableSpace = emptyDefaultBuffer.getSpace();

        assertEquals(DEFAULT_BUFFER_CAPACITY, availableSpace);
        assertTrue(hasSpaceForAtLeastOneByte);
    }
}
