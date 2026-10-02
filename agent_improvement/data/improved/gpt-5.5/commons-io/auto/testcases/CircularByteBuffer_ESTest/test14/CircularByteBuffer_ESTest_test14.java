package org.apache.commons.io.input.buffer;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CircularByteBuffer_ESTest_test14 extends CircularByteBuffer_ESTest_scaffolding {

    private static final int DEFAULT_BUFFER_CAPACITY = 8192;
    private static final int COMPARISON_BUFFER_SIZE = 6;
    private static final byte START_OFFSET = 0;
    private static final int BYTES_TO_COMPARE = 2;

    @Test(timeout = 4000)
    public void test14() throws Throwable {
        CircularByteBuffer emptyBuffer = new CircularByteBuffer();
        byte[] comparisonBytes = new byte[COMPARISON_BUFFER_SIZE];

        boolean matchesInitialBytes = emptyBuffer.peek(comparisonBytes, START_OFFSET, BYTES_TO_COMPARE);

        assertTrue(matchesInitialBytes);
        assertEquals(DEFAULT_BUFFER_CAPACITY, emptyBuffer.getSpace());
    }
}
