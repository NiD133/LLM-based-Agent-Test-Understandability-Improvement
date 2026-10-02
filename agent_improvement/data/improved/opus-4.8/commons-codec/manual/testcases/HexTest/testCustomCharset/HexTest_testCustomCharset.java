package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.UnsupportedEncodingException;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Verifies that {@link Hex} honours a custom charset when encoding and decoding.
 *
 * <p>The test is run once for every charset available on the running JVM, supplied by
 * {@code CharsetsTest#getAvailableCharsetNames()}.</p>
 */
public class HexTest_testCustomCharset {

    /** Plain-text message used as the encode/decode round-trip subject. */
    private static final String SOURCE_TEXT = "Hello World";

    /** The lower-case hex string for {@link #SOURCE_TEXT} encoded as UTF-8 ("Hello World"). */
    private static final String SOURCE_TEXT_AS_HEX = "48656c6c6f20576f726c64";

    /**
     * Confirms a charset can losslessly round-trip a sample sentence.
     *
     * <p>Some JVMs ship charsets that cannot faithfully reproduce ASCII text (or are not usable
     * for byte/string conversion at all). Such charsets are skipped because they would make the
     * encode/decode assertions meaningless rather than reveal a bug in {@link Hex}.</p>
     *
     * @param charsetName the charset to probe.
     * @return {@code true} if the charset round-trips the sample text, {@code false} otherwise.
     */
    private boolean canRoundTripText(final String charsetName) {
        final String sample = "the quick brown dog jumped over the lazy fox";
        try {
            final byte[] encoded = sample.getBytes(charsetName);
            final String decoded = new String(encoded, charsetName);
            return sample.equals(decoded);
        } catch (final UnsupportedEncodingException | UnsupportedOperationException e) {
            return false;
        }
    }

    @ParameterizedTest
    @MethodSource("org.apache.commons.codec.CharsetsTest#getAvailableCharsetNames()")
    void testCustomCharset(final String charsetName) throws UnsupportedEncodingException, DecoderException {
        // Skip charsets that cannot reliably represent the sample text on this JVM.
        if (!canRoundTripText(charsetName)) {
            return;
        }

        final Hex customCodec = new Hex(charsetName);
        final byte[] sourceBytes = SOURCE_TEXT.getBytes(charsetName);

        // Encoding through the custom codec must yield the hex string's bytes in that charset.
        final byte[] actualEncodedBytes = customCodec.encode(sourceBytes);
        final String expectedHexString = Hex.encodeHexString(sourceBytes);
        final byte[] expectedHexStringBytes = expectedHexString.getBytes(charsetName);
        assertArrayEquals(expectedHexStringBytes, actualEncodedBytes);

        // The encoded bytes, read back as text in the custom charset, must equal the hex string.
        assertEquals(expectedHexString, new String(actualEncodedBytes, charsetName), charsetName);

        // Sanity check: the default (UTF-8) codec decodes the known hex back to the source text.
        final Hex utf8Codec = new Hex();
        final byte[] decodedUtf8Bytes = (byte[]) utf8Codec.decode(SOURCE_TEXT_AS_HEX);
        assertEquals(SOURCE_TEXT, new String(decodedUtf8Bytes, utf8Codec.getCharset()), charsetName);

        // Actual check: the custom codec decodes its own output back to the original source text.
        final byte[] decodedCustomBytes = customCodec.decode(actualEncodedBytes);
        assertEquals(SOURCE_TEXT, new String(decodedCustomBytes, charsetName), charsetName);
    }
}
