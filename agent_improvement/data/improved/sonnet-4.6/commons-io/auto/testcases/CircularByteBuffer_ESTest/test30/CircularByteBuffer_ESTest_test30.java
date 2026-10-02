package org.apache.commons.io.input.buffer;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CircularByteBuffer_ESTest_test30 extends CircularByteBuffer_ESTest_scaffolding {

    // A freshly created buffer with capacity 1 and no bytes written should report 1 available space.
    @Test(timeout = 4000)
    public void test_getSpace_returnsFullCapacity_whenBufferIsEmpty() throws Throwable {
        CircularByteBuffer buffer = new CircularByteBuffer(1);
        int availableSpace = buffer.getSpace();
        assertEquals(1, availableSpace);
    }
}
