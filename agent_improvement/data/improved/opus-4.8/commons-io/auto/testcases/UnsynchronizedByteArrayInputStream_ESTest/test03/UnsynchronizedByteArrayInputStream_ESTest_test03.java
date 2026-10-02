package org.apache.commons.io.input;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class UnsynchronizedByteArrayInputStream_ESTest_test03 extends UnsynchronizedByteArrayInputStream_ESTest_scaffolding {

    /**
     * When the constructor is given an offset that is larger than the buffer
     * length, the offset is clamped to the buffer length, leaving the stream
     * already positioned at its end. Skipping over such an exhausted stream
     * therefore advances nothing and reports zero bytes skipped, and the
     * stream reports zero bytes available.
     */
    @Test(timeout = 4000)
    public void skipOnExhaustedStreamSkipsNothing() throws Throwable {
        // Buffer of length 2, but the requested offset (20) is past the end,
        // so the stream starts already exhausted.
        byte[] buffer = new byte[2];
        UnsynchronizedByteArrayInputStream stream =
                new UnsynchronizedByteArrayInputStream(buffer, 20);

        long bytesSkipped = stream.skip(20L);

        assertEquals("no bytes can be skipped on an exhausted stream", 0L, bytesSkipped);
        assertEquals("no bytes available on an exhausted stream", 0, stream.available());
    }
}
