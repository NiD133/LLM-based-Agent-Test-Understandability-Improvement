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
     * When the requested offset (358) is larger than the buffer length (8),
     * the constructor clamps the starting position to the end of the data.
     * The stream is therefore already exhausted: nothing is available to read
     * and {@code read(byte[])} immediately reports end-of-stream (-1).
     */
    @Test(timeout = 4000)
    public void offsetBeyondBufferYieldsExhaustedStream() throws Throwable {
        byte[] buffer = new byte[8];
        int offsetPastEnd = 358;
        UnsynchronizedByteArrayInputStream stream =
                new UnsynchronizedByteArrayInputStream(buffer, offsetPastEnd);

        assertEquals("no bytes should be available when offset is past the end", 0, stream.available());

        int bytesRead = stream.read(buffer);
        assertEquals("read on an exhausted stream returns end-of-stream", -1, bytesRead);
    }
}
