package org.apache.commons.io.input;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class UnsynchronizedByteArrayInputStream_ESTest_test05 extends UnsynchronizedByteArrayInputStream_ESTest_scaffolding {

    /**
     * Verifies that when the initial offset exceeds the buffer length, the stream is
     * immediately at end-of-stream: available() returns 0 and read() returns -1.
     *
     * The buffer has 8 bytes but the offset is 358, which is clamped to the buffer
     * length internally, so no bytes are available to read.
     */
    @Test(timeout = 4000)
    public void test05() throws Throwable {
        byte[] buffer = new byte[8];
        int offsetBeyondEnd = 358;

        UnsynchronizedByteArrayInputStream stream =
                new UnsynchronizedByteArrayInputStream(buffer, offsetBeyondEnd);

        assertEquals("Stream should report 0 available bytes when offset exceeds buffer length",
                0, stream.available());

        int bytesRead = stream.read(buffer);

        assertEquals("Reading from an exhausted stream should return -1 (end-of-stream)",
                -1, bytesRead);
    }
}
