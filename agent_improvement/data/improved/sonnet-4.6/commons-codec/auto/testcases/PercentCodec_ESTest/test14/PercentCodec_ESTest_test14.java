package org.apache.commons.codec.net;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class PercentCodec_ESTest_test14 extends PercentCodec_ESTest_scaffolding {

    /**
     * Verifies that encoding a plain ASCII control character (CR, byte 13) that is NOT in
     * the always-encode set returns the original byte array unchanged (same reference).
     *
     * The codec is configured with five null-byte (0) always-encode chars and plusForSpace=true.
     * Byte 13 (carriage return) is a non-negative ASCII byte that falls outside the
     * always-encode range, so no percent-encoding is applied and the input array is returned as-is.
     */
    @Test(timeout = 4000)
    public void test14() throws Throwable {
        // Build a codec whose "always encode" set consists of five null bytes (value 0) plus '%'
        byte[] alwaysEncodeChars = new byte[5]; // all elements default to 0
        PercentCodec codec = new PercentCodec(alwaysEncodeChars, /* plusForSpace */ true);

        // Input: a single carriage-return byte (ASCII 13), which is NOT in the always-encode set
        byte[] inputBytes = new byte[1];
        inputBytes[0] = (byte) 13; // carriage return (\r)

        byte[] encodedBytes = codec.encode(inputBytes);

        // No encoding is needed, so the codec must return the exact same array object
        assertSame(encodedBytes, inputBytes);
        assertNotNull(encodedBytes);
    }
}
