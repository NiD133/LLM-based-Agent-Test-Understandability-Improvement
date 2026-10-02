package org.apache.commons.codec.net;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class PercentCodec_ESTest_test11 extends PercentCodec_ESTest_scaffolding {

    // ASCII codes: '%' = 37, '0' = 48
    private static final byte PERCENT_SIGN = (byte) 37;
    private static final byte DIGIT_ZERO   = (byte) 48;
    private static final byte NULL_BYTE    = (byte) 0;

    @Test(timeout = 4000)
    public void test11() throws Throwable {
        // Build a codec that always percent-encodes the null byte (0x00).
        // With plusForSpace=false, spaces are not encoded as '+'.
        byte[] alwaysEncodeChars = new byte[] { NULL_BYTE };
        PercentCodec percentCodec0 = new PercentCodec(alwaysEncodeChars, false);

        // Encoding the null byte should produce the three-byte sequence "%00".
        byte[] encoded = percentCodec0.encode(alwaysEncodeChars);
        byte[] expected_encoded = new byte[] { PERCENT_SIGN, DIGIT_ZERO, DIGIT_ZERO };
        assertArrayEquals(expected_encoded, encoded);

        // Decoding "%00" back should restore the original null byte.
        byte[] decoded = percentCodec0.decode(encoded);
        byte[] expected_decoded = new byte[] { NULL_BYTE };
        assertArrayEquals(expected_decoded, decoded);
    }
}
