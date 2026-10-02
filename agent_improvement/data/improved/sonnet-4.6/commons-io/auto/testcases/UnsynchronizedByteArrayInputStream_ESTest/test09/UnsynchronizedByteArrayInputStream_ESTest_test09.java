package org.apache.commons.io.input;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class UnsynchronizedByteArrayInputStream_ESTest_test09 extends UnsynchronizedByteArrayInputStream_ESTest_scaffolding {

    /**
     * Verifies that available() returns the number of bytes remaining between the
     * starting offset and the end of the requested length window.
     *
     * Buffer: [0, 0] (2 bytes), offset=1, length=1
     * The stream starts at index 1 and covers 1 byte, so available() should return 1.
     */
    @Test(timeout = 4000)
    public void test_availableReturnsRemainingBytesInLengthWindow() throws Throwable {
        byte[] twoByteBuffer = new byte[2];
        // Construct stream starting at offset 1, covering 1 byte of the buffer
        UnsynchronizedByteArrayInputStream stream = new UnsynchronizedByteArrayInputStream(twoByteBuffer, 1, 1);

        int availableBytes = stream.available();

        assertEquals(1, availableBytes);
    }
}
