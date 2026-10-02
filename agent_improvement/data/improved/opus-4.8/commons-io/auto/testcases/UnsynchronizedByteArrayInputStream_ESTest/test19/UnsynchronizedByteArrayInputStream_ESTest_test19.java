package org.apache.commons.io.input;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class UnsynchronizedByteArrayInputStream_ESTest_test19 extends UnsynchronizedByteArrayInputStream_ESTest_scaffolding {

    /**
     * When the starting offset is positioned at the very end of the readable data,
     * {@code available()} should report that no bytes remain.
     *
     * <p>The stream is built over a single-byte buffer with both the offset and the
     * length set to 1. The constructor clamps these against the buffer size, so the
     * read position already sits at end-of-data and nothing is left to read.</p>
     */
    @Test(timeout = 4000)
    public void availableIsZeroWhenOffsetIsAtEndOfData() throws Throwable {
        byte[] singleByteBuffer = new byte[1];
        int offset = 1;
        int length = 1;
        UnsynchronizedByteArrayInputStream stream =
                new UnsynchronizedByteArrayInputStream(singleByteBuffer, offset, length);

        int remainingBytes = stream.available();

        assertEquals(0, remainingBytes);
    }
}
