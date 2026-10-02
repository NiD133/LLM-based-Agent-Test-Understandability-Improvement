package org.apache.commons.io.input;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class UnsynchronizedByteArrayInputStream_ESTest_test11 extends UnsynchronizedByteArrayInputStream_ESTest_scaffolding {

    /**
     * When the stream wraps an empty buffer, no bytes can ever be read, so
     * {@code available()} must report 0 regardless of the requested offset.
     */
    @Test(timeout = 4000)
    public void availableIsZeroForEmptyBuffer() throws Throwable {
        byte[] emptyBuffer = new byte[0];
        int offset = 1;

        UnsynchronizedByteArrayInputStream stream =
                new UnsynchronizedByteArrayInputStream(emptyBuffer, offset);

        assertEquals(0, stream.available());
    }
}
