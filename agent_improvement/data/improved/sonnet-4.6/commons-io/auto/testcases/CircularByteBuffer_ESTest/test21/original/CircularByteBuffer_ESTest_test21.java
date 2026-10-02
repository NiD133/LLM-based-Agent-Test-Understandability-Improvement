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
    public void test21() throws Throwable {
        byte[] byteArray0 = new byte[16];
        CircularByteBuffer circularByteBuffer0 = new CircularByteBuffer((byte) 8);
        circularByteBuffer0.add(byteArray0, (int) (byte) 8, (int) (byte) 8);
        assertEquals(8, circularByteBuffer0.getCurrentNumberOfBytes());
        circularByteBuffer0.read(byteArray0, (int) (byte) 8, (int) (byte) 8);
        assertEquals(8, circularByteBuffer0.getSpace());
    }
}
