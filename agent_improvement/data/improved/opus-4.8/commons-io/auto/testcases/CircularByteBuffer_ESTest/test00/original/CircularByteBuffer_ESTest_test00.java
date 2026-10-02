package org.apache.commons.io.input.buffer;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CircularByteBuffer_ESTest_test00 extends CircularByteBuffer_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test00() throws Throwable {
        CircularByteBuffer circularByteBuffer0 = new CircularByteBuffer(260);
        byte[] byteArray0 = new byte[1];
        circularByteBuffer0.read(byteArray0, 0, 0);
        assertEquals(260, circularByteBuffer0.getSpace());
    }
}
