package org.apache.commons.io.input;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class UnsynchronizedByteArrayInputStream_ESTest_test07 extends UnsynchronizedByteArrayInputStream_ESTest_scaffolding {

    /**
     * Reads the single available byte from a stream that exposes a one-byte window
     * (offset 1, length 1) over a three-byte buffer, then verifies the stream is
     * fully consumed.
     */
    @Test(timeout = 4000)
    public void readConsumesSingleByteWindowThenReportsNothingAvailable() throws Throwable {
        // Buffer is all zeros; the stream only exposes the byte at index 1.
        byte[] buffer = new byte[3];
        UnsynchronizedByteArrayInputStream stream =
                new UnsynchronizedByteArrayInputStream(buffer, 1, 1);

        int byteValue = stream.read();

        // The single exposed byte (value 0) was read, leaving nothing available.
        assertEquals(0, byteValue);
        assertEquals(0, stream.available());
    }
}
