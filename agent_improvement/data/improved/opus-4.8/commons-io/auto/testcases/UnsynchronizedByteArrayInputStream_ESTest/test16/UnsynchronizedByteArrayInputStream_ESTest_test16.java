package org.apache.commons.io.input;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class UnsynchronizedByteArrayInputStream_ESTest_test16 extends UnsynchronizedByteArrayInputStream_ESTest_scaffolding {

    /**
     * Verifies that calling reset() on a freshly created stream leaves all
     * bytes available to be read. Since no byte has been consumed yet, the
     * mark is still at the start of the buffer, so reset() keeps the read
     * position at offset 0 and available() reports the full buffer length.
     */
    @Test(timeout = 4000)
    public void resetOnUnreadStreamKeepsAllBytesAvailable() throws Throwable {
        byte[] buffer = new byte[2];
        UnsynchronizedByteArrayInputStream stream = new UnsynchronizedByteArrayInputStream(buffer);

        stream.reset();

        int expectedAvailableBytes = 2;
        assertEquals(expectedAvailableBytes, stream.available());
    }
}
