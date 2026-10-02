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
     * When the stream is constructed with an offset larger than the data length,
     * the position is clamped to the end of data. Any subsequent skip() call
     * should return 0 (nothing to skip) and available() should return 0.
     */
    @Test(timeout = 4000)
    public void test03() throws Throwable {
        // A 2-byte array; offset 20 exceeds the array length, so the stream starts at end-of-data
        byte[] twoByteData = new byte[2];
        int offsetBeyondEnd = (byte) 20; // 20 > data.length (2), clamped to end-of-data on construction

        UnsynchronizedByteArrayInputStream stream =
                new UnsynchronizedByteArrayInputStream(twoByteData, offsetBeyondEnd);

        // Stream is already at end-of-data, so skip() has nothing to advance over
        long bytesSkipped = stream.skip((byte) 20);

        assertEquals("No bytes should be available when stream starts beyond data end", 0, stream.available());
        assertEquals("Skip should return 0 when there is nothing left to skip", 0L, bytesSkipped);
    }
}
