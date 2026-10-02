package org.apache.commons.io.input.buffer;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CircularByteBuffer_ESTest_test09 extends CircularByteBuffer_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test09() throws Throwable {
        byte[] comparisonBytes = new byte[6];
        CircularByteBuffer buffer = new CircularByteBuffer(2);

        buffer.add((byte) (-3));
        assertTrue(buffer.hasBytes());

        buffer.read();
        boolean peekMatchesAfterRead = buffer.peek(comparisonBytes, (byte) 0, 2);

        assertFalse(peekMatchesAfterRead);
    }
}
