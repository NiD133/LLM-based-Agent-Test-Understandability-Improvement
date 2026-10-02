package org.apache.commons.io.input;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class UnsynchronizedByteArrayInputStream_ESTest_test01 extends UnsynchronizedByteArrayInputStream_ESTest_scaffolding {

    /**
     * Verifies that skipping zero bytes leaves the stream position unchanged
     * and all bytes remain available for reading.
     */
    @Test(timeout = 4000)
    public void test01_skipZeroBytes_doesNotAdvancePosition() throws Throwable {
        // Create a 6-byte stream with default (zero) content
        byte[] buffer = new byte[6];
        UnsynchronizedByteArrayInputStream stream = new UnsynchronizedByteArrayInputStream(buffer);

        // Skipping 0 bytes should return 0 (no bytes actually skipped)
        long bytesSkipped = stream.skip(0L);
        assertEquals(0L, bytesSkipped);

        // All 6 bytes should still be available since position was not advanced
        assertEquals(6, stream.available());
    }
}
