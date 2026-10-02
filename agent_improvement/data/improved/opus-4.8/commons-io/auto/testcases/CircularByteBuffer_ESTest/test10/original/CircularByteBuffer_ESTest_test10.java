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
        CircularByteBuffer circularByteBuffer0 = new CircularByteBuffer();
        byte[] byteArray0 = new byte[4];
        circularByteBuffer0.add(byteArray0, 2, 2);
        circularByteBuffer0.add(byteArray0, 2, 2);
        boolean boolean0 = circularByteBuffer0.peek(byteArray0, 1, 2);
        assertEquals(8188, circularByteBuffer0.getSpace());
        assertFalse(boolean0);
    }
}
