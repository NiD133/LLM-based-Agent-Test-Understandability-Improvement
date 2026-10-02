package org.apache.commons.io.input.buffer;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CircularByteBuffer_ESTest_test29 extends CircularByteBuffer_ESTest_scaffolding {

    // Default buffer size matches IOUtils.DEFAULT_BUFFER_SIZE
    private static final int DEFAULT_BUFFER_SIZE = 8192;

    @Test(timeout = 4000)
    public void test_clearOnNewBuffer_restoresFullCapacity() throws Throwable {
        CircularByteBuffer buffer = new CircularByteBuffer();

        buffer.clear();

        assertEquals("After clear(), the entire buffer capacity should be available as free space",
                DEFAULT_BUFFER_SIZE, buffer.getSpace());
    }
}
