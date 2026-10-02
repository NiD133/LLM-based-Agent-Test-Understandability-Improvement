package org.apache.commons.codec.binary;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Hex_ESTest_test18 extends Hex_ESTest_scaffolding {

    /**
     * Decoding fails when the input bytes are not valid hexadecimal text.
     *
     * <p>The flow is:</p>
     * <ol>
     *   <li>encode a plain string to its hex representation (bytes of hex digits),</li>
     *   <li>decode that hex representation back to the original string's raw bytes,</li>
     *   <li>attempt to decode those raw bytes again - they are not hex digits, so
     *       {@link org.apache.commons.codec.DecoderException} is thrown by {@code Hex}.</li>
     * </ol>
     */
    @Test(timeout = 4000)
    public void decodingNonHexBytesThrowsException() throws Throwable {
        Hex hex = new Hex();
        String plainText = "5TuU>'M{Jxu_";

        // Hex representation of plainText, returned as a byte[] of hex-digit characters.
        Object hexEncodedBytes = hex.encode((Object) plainText);

        // Decoding the hex representation yields the raw bytes of the original plain text.
        Object plainTextBytes = hex.decode(hexEncodedBytes);

        // The raw plain-text bytes contain non-hex characters (e.g. 'T'),
        // so decoding them as hexadecimal must fail.
        try {
            hex.decode(plainTextBytes);
            fail("Expecting a DecoderException for illegal hexadecimal characters");
        } catch (Exception e) {
            verifyException("org.apache.commons.codec.binary.Hex", e);
        }
    }
}
