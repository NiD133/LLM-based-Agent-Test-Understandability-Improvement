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

    /**
     * Decoding input that ends with a lone escape character ('%') must fail.
     *
     * <p>A valid percent-encoded escape needs two hex digits after the '%'
     * (e.g. "%25"). Here the '%' is the last byte, so {@code decode} runs off
     * the end of the array while reading those digits and reports an
     * "Invalid percent decoding" error.</p>
     */
    @Test(timeout = 4000)
    public void decodeTruncatedPercentEscapeThrowsException() throws Throwable {
        // Codec configuration is irrelevant to this failure; use defaults.
        byte[] alwaysEncodeChars = new byte[1];
        PercentCodec percentCodec = new PercentCodec(alwaysEncodeChars, true);

        // Input whose final byte is the escape char '%' with no following hex digits.
        byte[] truncatedEscape = new byte[9];
        truncatedEscape[8] = (byte) '%';

        try {
            percentCodec.decode(truncatedEscape);
            fail("Expecting exception: Exception");
        } catch (Exception e) {
            // Thrown from PercentCodec.decode as "Invalid percent decoding: ".
            verifyException("org.apache.commons.codec.net.PercentCodec", e);
        }
    }
}
