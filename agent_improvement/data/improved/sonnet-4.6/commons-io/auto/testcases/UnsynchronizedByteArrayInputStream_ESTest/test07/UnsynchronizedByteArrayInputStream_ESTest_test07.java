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
     * Verifies that reading the single byte within the stream window returns the correct byte value
     * and leaves no bytes available afterward.
     *
     * The buffer is a 3-byte zero array. The stream is created with offset=1 and length=1,
     * so only the byte at index 1 (value 0) is visible. After reading it, available() must be 0.
     */
    @Test(timeout = 4000)
    public void test07() throws Throwable {
        // Buffer: [0, 0, 0] — all bytes default to zero
        byte[] buffer = new byte[3];

        // Expose only one byte starting at index 1 (the middle byte, value 0)
        UnsynchronizedByteArrayInputStream stream = new UnsynchronizedByteArrayInputStream(buffer, 1, 1);

        // Read the single exposed byte; it should be 0 (the unsigned value of byte 0)
        int byteRead = stream.read();

        // After reading the only available byte, nothing should remain
        assertEquals(0, stream.available());
        assertEquals(0, byteRead);
    }
}
