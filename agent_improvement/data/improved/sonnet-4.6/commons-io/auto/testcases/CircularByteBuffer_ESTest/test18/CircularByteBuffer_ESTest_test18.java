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

    private static final int DEFAULT_BUFFER_SIZE = 8192;

    @Test(timeout = 4000)
    public void test_newBufferHasSpaceForAllDefaultCapacityBytes() throws Throwable {
        CircularByteBuffer buffer = new CircularByteBuffer();

        boolean hasSpace = buffer.hasSpace();

        assertEquals("A freshly created buffer should have all " + DEFAULT_BUFFER_SIZE + " bytes available",
                DEFAULT_BUFFER_SIZE, buffer.getSpace());
        assertTrue("A freshly created buffer should report that space is available", hasSpace);
    }
}
