package org.apache.commons.io.input.buffer;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CircularByteBuffer_ESTest_test19 extends CircularByteBuffer_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test_hasSpace_returnsFalse_whenBufferCapacityIsZero() throws Throwable {
        // A buffer created with capacity 0 has no room for any bytes
        CircularByteBuffer zeroCapacityBuffer = new CircularByteBuffer(0);

        boolean hasSpace = zeroCapacityBuffer.hasSpace();

        assertFalse("A zero-capacity buffer should report no available space", hasSpace);
    }
}
