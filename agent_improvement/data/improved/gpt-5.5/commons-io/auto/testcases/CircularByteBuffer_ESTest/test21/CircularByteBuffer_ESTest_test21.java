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
        final int backingArraySize = 16;
        final int bufferCapacity = 8;
        final int arrayOffset = 8;
        final int byteCount = 8;

        byte[] bytes = new byte[backingArraySize];
        CircularByteBuffer circularByteBuffer = new CircularByteBuffer((byte) bufferCapacity);

        circularByteBuffer.add(bytes, (int) (byte) arrayOffset, (int) (byte) byteCount);
        assertEquals(byteCount, circularByteBuffer.getCurrentNumberOfBytes());

        circularByteBuffer.read(bytes, (int) (byte) arrayOffset, (int) (byte) byteCount);
        assertEquals(bufferCapacity, circularByteBuffer.getSpace());
    }
}
