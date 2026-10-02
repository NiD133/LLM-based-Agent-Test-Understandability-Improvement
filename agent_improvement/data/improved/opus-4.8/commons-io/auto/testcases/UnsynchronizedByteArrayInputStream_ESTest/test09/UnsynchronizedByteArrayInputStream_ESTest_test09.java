package org.apache.commons.io.input;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class UnsynchronizedByteArrayInputStream_ESTest_test09 extends UnsynchronizedByteArrayInputStream_ESTest_scaffolding {

    /**
     * Verifies that available() reflects the configured length when the stream is
     * created with an offset and length within the bounds of the backing array.
     *
     * The stream wraps a 2-byte buffer but starts reading at offset 1 with a
     * length of 1, so exactly 1 byte remains available.
     */
    @Test(timeout = 4000)
    public void availableReturnsRemainingBytesForOffsetAndLength() throws Throwable {
        byte[] buffer = new byte[2];
        int offset = 1;
        int length = 1;
        UnsynchronizedByteArrayInputStream inputStream =
                new UnsynchronizedByteArrayInputStream(buffer, offset, length);

        int availableBytes = inputStream.available();

        assertEquals(1, availableBytes);
    }
}
