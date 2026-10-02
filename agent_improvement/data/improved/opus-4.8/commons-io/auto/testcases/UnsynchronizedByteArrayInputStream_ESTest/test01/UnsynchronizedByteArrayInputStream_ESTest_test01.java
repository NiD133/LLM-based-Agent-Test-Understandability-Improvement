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
     * Verifies that skipping zero bytes is a no-op: it reports that no bytes were
     * skipped and leaves the stream's available byte count unchanged.
     */
    @Test(timeout = 4000)
    public void skipZeroBytesDoesNotConsumeAnyInput() throws Throwable {
        // Build a stream backed by a 6-byte buffer.
        byte[] buffer = new byte[6];
        UnsynchronizedByteArrayInputStream stream = new UnsynchronizedByteArrayInputStream(buffer);

        // Skipping 0 bytes should report 0 bytes skipped.
        long bytesSkipped = stream.skip(0L);
        assertEquals(0L, bytesSkipped);

        // All 6 bytes remain available since nothing was consumed.
        assertEquals(6, stream.available());
    }
}
