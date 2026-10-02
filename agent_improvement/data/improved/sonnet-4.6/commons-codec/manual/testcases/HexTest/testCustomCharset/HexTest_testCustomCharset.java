package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.UnsupportedEncodingException;

import org.apache.commons.codec.DecoderException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class HexTest_testCustomCharset {

    /**
     * Verifies that the Hex codec configured with a custom charset correctly round-trips
     * "Hello World" through hex encoding and decoding.
     *
     * <p>Checks performed:
     * <ol>
     *   <li>Encoding source bytes via the custom codec produces the same bytes as calling
     *       {@link Hex#encodeHexString} and then getting those string bytes in the custom charset.</li>
     *   <li>Decoding those hex bytes with the custom codec recovers the original string.</li>
     *   <li>A UTF-8 Hex codec correctly decodes the known hex literal "48656c6c6f20576f726c64"
     *       to "Hello World" (sanity check).</li>
     * </ol>
     */
    private void testCharset(final String charsetName, final String callerLabel)
            throws UnsupportedEncodingException, DecoderException {

        // Skip charsets that do not support ASCII round-trips (e.g. JIS_X0212-1990).
        if (!isCharsetSane(charsetName)) {
            return;
        }

        final Hex customCodec = new Hex(charsetName);

        final String sourceString = "Hello World";
        final byte[] sourceBytes = sourceString.getBytes(charsetName);

        // --- Encoding check ---
        // The custom codec should encode the bytes into hex characters using the custom charset.
        final byte[] actualEncodedBytes = customCodec.encode(sourceBytes);
        final String expectedHexString = Hex.encodeHexString(sourceBytes);
        final byte[] expectedEncodedBytes = expectedHexString.getBytes(charsetName);
        assertArrayEquals(expectedEncodedBytes, actualEncodedBytes,
                "encode() output should match encodeHexString() bytes in charset " + charsetName);

        // The encoded bytes, when interpreted as a string in the custom charset, should equal the hex string.
        final String hexStringFromEncoded = new String(actualEncodedBytes, charsetName);
        assertEquals(expectedHexString, hexStringFromEncoded, charsetName);

        // --- Decoding sanity check using the UTF-8 Hex codec ---
        final Hex utf8Codec = new Hex();
        final String knownHexOfHelloWorld = "48656c6c6f20576f726c64";
        final byte[] decodedUtf8Bytes = (byte[]) utf8Codec.decode(knownHexOfHelloWorld);
        final String decodedUtf8String = new String(decodedUtf8Bytes, utf8Codec.getCharset());
        assertEquals(sourceString, decodedUtf8String,
                "UTF-8 Hex codec must decode the known hex literal to 'Hello World'");

        // --- Round-trip decoding check with the custom codec ---
        final byte[] decodedCustomBytes = customCodec.decode(actualEncodedBytes);
        final String decodedCustomString = new String(decodedCustomBytes, charsetName);
        assertEquals(sourceString, decodedCustomString,
                "Custom charset codec must round-trip 'Hello World' through encode then decode");
    }

    /**
     * Returns {@code false} for charsets that cannot faithfully round-trip an ASCII string
     * (e.g. some JIS variants on certain JVMs).
     */
    private boolean isCharsetSane(final String name) {
        final String probe = "the quick brown dog jumped over the lazy fox";
        try {
            final byte[] bytes = probe.getBytes(name);
            return probe.equals(new String(bytes, name));
        } catch (final UnsupportedEncodingException | UnsupportedOperationException e) {
            return false;
        }
    }

    @ParameterizedTest
    @MethodSource("org.apache.commons.codec.CharsetsTest#getAvailableCharsetNames()")
    void testCustomCharset(final String name) throws UnsupportedEncodingException, DecoderException {
        testCharset(name, "testCustomCharset");
    }
}
