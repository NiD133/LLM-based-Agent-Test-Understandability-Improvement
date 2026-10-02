package org.apache.commons.io.input.buffer;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CircularByteBuffer_ESTest_test19 extends CircularByteBuffer_ESTest_scaffolding {

    /**
     * A buffer created with capacity 0 can never hold a byte,
     * so hasSpace() must report that there is no room.
     */
    @Test(timeout = 4000)
    public void hasSpaceReturnsFalseForZeroCapacityBuffer() throws Throwable {
        CircularByteBuffer zeroCapacityBuffer = new CircularByteBuffer(0);

        boolean roomForOneByte = zeroCapacityBuffer.hasSpace();

        assertFalse(roomForOneByte);
    }
}
