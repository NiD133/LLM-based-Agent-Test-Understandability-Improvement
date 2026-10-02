package org.apache.commons.codec.net;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.util.BitSet;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class URLCodec_ESTest_test13 extends URLCodec_ESTest_scaffolding {

    /**
     * Verifies that passing a null BitSet to encodeUrl causes it to fall back to the
     * default WWW_FORM_URL_SAFE character set, and that every byte in the input that
     * is not URL-safe is percent-encoded as three ASCII bytes (%XX).
     *
     * Input: 8 bytes — first byte is 0xDA (decimal -38 as signed byte, a non-ASCII
     * value), remaining seven bytes are 0x00 (null). None of these are URL-safe.
     * Expected output length: 8 bytes × 3 bytes each (%XX) = 24 bytes.
     */
    @Test(timeout = 4000)
    public void test13() throws Throwable {
        // Build an 8-byte input where the first byte is a non-ASCII value (0xDA)
        // and the remaining seven bytes are null (0x00). All are outside the
        // default URL-safe character set, so each will be percent-encoded.
        byte[] inputBytes = new byte[8];
        inputBytes[0] = (byte) (-38); // 0xDA — a non-ASCII byte, not URL-safe

        // Encode with a null safe-set; URLCodec falls back to WWW_FORM_URL_SAFE.
        // Every non-safe byte expands to 3 ASCII characters: '%', hex-high, hex-low.
        byte[] encodedBytes = URLCodec.encodeUrl((BitSet) null, inputBytes);

        // 8 input bytes × 3 encoded bytes per non-safe byte = 24 bytes
        assertEquals(24, encodedBytes.length);
    }
}
