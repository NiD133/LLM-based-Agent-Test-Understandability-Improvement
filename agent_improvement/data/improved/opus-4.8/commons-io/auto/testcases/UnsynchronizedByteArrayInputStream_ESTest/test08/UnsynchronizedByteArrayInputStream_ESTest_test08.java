package org.apache.commons.io.input;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class UnsynchronizedByteArrayInputStream_ESTest_test08 extends UnsynchronizedByteArrayInputStream_ESTest_scaffolding {

    /**
     * When the starting offset is positioned at (or past) the end of the
     * readable data, the stream is already exhausted: {@code available()}
     * reports zero remaining bytes and {@code read()} returns END_OF_STREAM (-1).
     */
    @Test(timeout = 4000)
    public void readAtEndOfStreamReturnsEndOfStream() throws Throwable {
        // A one-byte buffer opened with offset == 1 leaves no readable bytes.
        byte[] buffer = new byte[1];
        UnsynchronizedByteArrayInputStream stream =
                new UnsynchronizedByteArrayInputStream(buffer, 1, 1);

        // No bytes remain to be read.
        assertEquals(0, stream.available());

        // Reading from an exhausted stream signals end of stream.
        int result = stream.read();
        assertEquals(-1, result);
    }
}
