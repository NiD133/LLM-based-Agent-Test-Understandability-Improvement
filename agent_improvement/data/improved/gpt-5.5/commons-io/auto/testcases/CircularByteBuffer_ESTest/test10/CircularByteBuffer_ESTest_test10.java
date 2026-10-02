package org.apache.commons.io.input.buffer;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CircularByteBuffer_ESTest_test10 extends CircularByteBuffer_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test10() throws Throwable {
        CircularByteBuffer buffer = new CircularByteBuffer();
        byte[] sourceBytes = new byte[4];

        buffer.add(sourceBytes, 2, 2);
        buffer.add(sourceBytes, 2, 2);

        boolean hasExpectedPrefix = buffer.peek(sourceBytes, 1, 2);

        assertEquals(8188, buffer.getSpace());
        assertFalse(hasExpectedPrefix);
    }
}
