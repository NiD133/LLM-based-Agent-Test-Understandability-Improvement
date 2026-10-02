package org.apache.commons.codec.net;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class PercentCodec_ESTest_test00 extends PercentCodec_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test_decode_throwsException_whenPercentSignAppearsAtEndWithNoFollowingHexDigits() throws Throwable {
        // Codec configured to always encode the single byte 0x00, with '+' for spaces
        byte[] alwaysEncodeBytes = new byte[1];
        PercentCodec percentCodec = new PercentCodec(alwaysEncodeBytes, true);

        // Input has '%' (0x25) at position 8, with no subsequent bytes for the required two hex digits
        byte[] inputWithTrailingPercent = new byte[9];
        inputWithTrailingPercent[8] = (byte) 37; // '%'

        try {
            percentCodec.decode(inputWithTrailingPercent);
            fail("Expecting exception: Exception");
        } catch (Exception e) {
            //
            // Invalid percent decoding:
            //
            verifyException("org.apache.commons.codec.net.PercentCodec", e);
        }
    }
}
