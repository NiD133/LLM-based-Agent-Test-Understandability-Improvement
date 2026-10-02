package org.apache.commons.io.input.buffer;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CircularByteBuffer_ESTest_test07 extends CircularByteBuffer_ESTest_scaffolding {

    private static final int TWO_BYTE_CAPACITY = 2;
    private static final byte FIRST_VALUE = (byte) (-3);
    private static final byte SECOND_VALUE = (byte) 33;

    @Test(timeout = 4000)
    public void test07() throws Throwable {
        CircularByteBuffer buffer = new CircularByteBuffer(TWO_BYTE_CAPACITY);

        buffer.add(FIRST_VALUE);
        buffer.read();
        buffer.add(SECOND_VALUE);

        assertTrue(buffer.hasBytes());
        byte actualValue = buffer.read();
        assertEquals(SECOND_VALUE, actualValue);
    }
}
