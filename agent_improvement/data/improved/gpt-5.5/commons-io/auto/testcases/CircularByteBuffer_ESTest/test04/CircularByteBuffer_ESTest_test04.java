package org.apache.commons.io.input.buffer;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CircularByteBuffer_ESTest_test04 extends CircularByteBuffer_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test04() throws Throwable {
        final int bufferCapacity = 47;
        final int illegalTargetOffset = 47;
        final int requestedLength = 47;
        final byte[] emptyTargetBuffer = new byte[0];
        final CircularByteBuffer circularByteBuffer = new CircularByteBuffer(bufferCapacity);

        try {
            circularByteBuffer.read(emptyTargetBuffer, illegalTargetOffset, requestedLength);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            verifyException("org.apache.commons.io.input.buffer.CircularByteBuffer", e);
        }
    }
}
