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

    @Test(timeout = 4000)
    public void test07() throws Throwable {
        CircularByteBuffer circularByteBuffer0 = new CircularByteBuffer(2);
        circularByteBuffer0.add((byte) (-3));
        circularByteBuffer0.read();
        circularByteBuffer0.add((byte) 33);
        assertTrue(circularByteBuffer0.hasBytes());
        byte byte0 = circularByteBuffer0.read();
        assertEquals((byte) 33, byte0);
    }
}
