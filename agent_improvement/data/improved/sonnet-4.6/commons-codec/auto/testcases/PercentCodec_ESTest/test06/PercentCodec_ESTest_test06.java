package org.apache.commons.codec.net;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class PercentCodec_ESTest_test06 extends PercentCodec_ESTest_scaffolding {

    /**
     * Verifies double percent-encoding: encoding a null byte (0x00) twice.
     *
     * Round 1: [0x00] → "%00"  (bytes: 37, 48, 48)
     * Round 2: "%00" → "%2500" because '%' (0x25) is itself always-encoded
     *           '%' → "%25", '0' → '0', '0' → '0'
     *
     * Expected final bytes represent the ASCII string "%2500".
     */
    @Test(timeout = 4000)
    public void test06() throws Throwable {
        // A single null byte (0x00) that is registered as a character to always encode.
        byte[] nullByteInput = new byte[1]; // value is 0x00 by default

        // Configure the codec to always encode 0x00 (and implicitly '%') without plus-for-space.
        PercentCodec percentCodec = new PercentCodec(nullByteInput, false);

        // First encoding: 0x00 → '%' '0' '0'  (i.e. the bytes 37, 48, 48)
        byte[] firstEncoding = percentCodec.encode(nullByteInput);

        // Second encoding: '%' is always encoded, so '%00' → '%25' '0' '0' = "%2500"
        byte[] secondEncoding = percentCodec.encode(firstEncoding);

        // "%2500" as raw bytes: '%'=37, '2'=50, '5'=53, '0'=48, '0'=48
        byte[] expectedDoubleEncoded = { (byte) 37, (byte) 50, (byte) 53, (byte) 48, (byte) 48 };
        assertArrayEquals(expectedDoubleEncoded, secondEncoding);
    }
}
