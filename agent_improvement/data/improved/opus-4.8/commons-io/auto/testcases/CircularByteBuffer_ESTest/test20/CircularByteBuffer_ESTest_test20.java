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

    /**
     * A freshly constructed buffer holds no bytes and reports its full
     * capacity as available space.
     */
    @Test(timeout = 4000)
    public void newBufferIsEmptyAndHasFullCapacityAsSpace() throws Throwable {
        final int capacity = 260;
        CircularByteBuffer buffer = new CircularByteBuffer(capacity);

        assertFalse("a new buffer should contain no bytes", buffer.hasBytes());
        assertEquals("a new buffer's free space should equal its capacity",
                capacity, buffer.getSpace());
    }
}
