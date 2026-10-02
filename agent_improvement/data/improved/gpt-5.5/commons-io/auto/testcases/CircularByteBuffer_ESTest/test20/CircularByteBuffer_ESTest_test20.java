package org.apache.commons.io.input.buffer;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CircularByteBuffer_ESTest_test20 extends CircularByteBuffer_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test20() throws Throwable {
        CircularByteBuffer emptyBuffer = new CircularByteBuffer(260);

        boolean hasBufferedBytes = emptyBuffer.hasBytes();

        assertFalse(hasBufferedBytes);
        assertEquals(260, emptyBuffer.getSpace());
    }
}
