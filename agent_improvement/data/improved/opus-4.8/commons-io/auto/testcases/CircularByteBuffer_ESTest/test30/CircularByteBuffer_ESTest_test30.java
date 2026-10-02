package org.apache.commons.io.input.buffer;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CircularByteBuffer_ESTest_test30 extends CircularByteBuffer_ESTest_scaffolding {

    /**
     * A freshly created buffer with capacity N has all N bytes free,
     * so getSpace() should report the full capacity.
     */
    @Test(timeout = 4000)
    public void getSpaceOnEmptyBufferReturnsFullCapacity() throws Throwable {
        CircularByteBuffer buffer = new CircularByteBuffer(1);

        int availableSpace = buffer.getSpace();

        assertEquals(1, availableSpace);
    }
}
