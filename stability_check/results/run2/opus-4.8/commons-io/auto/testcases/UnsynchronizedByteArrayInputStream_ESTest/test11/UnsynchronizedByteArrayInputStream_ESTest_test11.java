package org.apache.commons.io.input;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class UnsynchronizedByteArrayInputStream_ESTest_test11 extends UnsynchronizedByteArrayInputStream_ESTest_scaffolding {

    /**
     * When the stream is created from an empty buffer, no bytes can ever be read,
     * so {@link UnsynchronizedByteArrayInputStream#available()} should report 0 —
     * even when a positive offset is requested (the offset is clamped to the buffer length).
     */
    @Test(timeout = 4000)
    public void availableIsZeroForEmptyBuffer() throws Throwable {
        byte[] emptyBuffer = new byte[0];
        int requestedOffset = 1;

        UnsynchronizedByteArrayInputStream stream =
                new UnsynchronizedByteArrayInputStream(emptyBuffer, requestedOffset);

        assertEquals(0, stream.available());
    }
}
