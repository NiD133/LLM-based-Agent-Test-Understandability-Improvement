package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import org.apache.commons.codec.DecoderException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class HexTest_testRequiredCharset {

    private static final boolean LOG = false;

    /**
     * Returns false for charsets where encode→decode does not round-trip the ASCII test sentence,
     * or where the charset is not supported by the JVM. Such charsets are skipped in the tests.
     */
    private boolean charsetSanityCheck(final String name) {
        final String source = "the quick brown dog jumped over the lazy fox";
        try {
            final byte[] bytes = source.getBytes(name);
            final String roundTripped = new String(bytes, name);
            final boolean equals = source.equals(roundTripped);
            if (!equals) {
                log("FAILED charsetSanityCheck=Interesting Java charset oddity: Roundtrip failed for " + name);
            }
            return equals;
        } catch (final UnsupportedEncodingException | UnsupportedOperationException e) {
            if (LOG) {
                log("FAILED charsetSanityCheck=" + name + ", e=" + e);
                log(e);
            }
            return false;
        }
    }

    private void log(final String s) {
        if (LOG) {
            System.out.println(s);
            System.out.flush();
        }
    }

    private void log(final Throwable t) {
        if (LOG) {
            t.printStackTrace(System.out);
            System.out.flush();
        }
    }

    /**
     * Verifies that a {@link Hex} codec configured with the given charset correctly encodes and
     * decodes the string {@code "Hello World"}.
     *
     * <p><b>Part 1 – encode:</b> Encoding the source bytes with the custom-charset codec must
     * produce exactly the same bytes as hex-encoding the source bytes and then interpreting the
     * resulting hex string in the target charset.
     *
     * <p><b>Part 2 – decode:</b> The UTF-8 codec must decode the well-known hex literal
     * {@code "48656c6c6f20576f726c64"} back to {@code "Hello World"}, and the custom-charset
     * codec must decode its own encoded output back to {@code "Hello World"}.
     */
    private void testCharset(final String charsetName) throws UnsupportedEncodingException, DecoderException {
        if (!charsetSanityCheck(charsetName)) {
            return;
        }
        log("testRequiredCharset=" + charsetName);

        final Hex customCharsetCodec = new Hex(charsetName);
        final String sourceString = "Hello World";
        final byte[] sourceBytes = sourceString.getBytes(charsetName);

        // Part 1: encoding source bytes with the custom codec should produce
        // hex characters re-encoded in the same charset.
        final byte[] actualEncodedBytes = customCharsetCodec.encode(sourceBytes);
        final String expectedHexString = Hex.encodeHexString(sourceBytes);
        final byte[] expectedHexStringBytes = expectedHexString.getBytes(charsetName);
        assertArrayEquals(expectedHexStringBytes, actualEncodedBytes);

        // Sanity-check: the encoded byte array decodes back to the expected hex string.
        final String actualHexString = new String(actualEncodedBytes, charsetName);
        assertEquals(expectedHexString, actualHexString, charsetName);

        // Part 2: the UTF-8 codec decodes the known hex literal for "Hello World".
        final Hex utf8Codec = new Hex();
        final String helloWorldHex = "48656c6c6f20576f726c64";
        final byte[] utf8DecodedBytes = (byte[]) utf8Codec.decode(helloWorldHex);
        final String utf8DecodedString = new String(utf8DecodedBytes, utf8Codec.getCharset());
        assertEquals(sourceString, utf8DecodedString, charsetName);

        // The custom-charset codec must also decode its own encoded output back to the original.
        final byte[] customDecodedBytes = customCharsetCodec.decode(actualEncodedBytes);
        final String customDecodedString = new String(customDecodedBytes, charsetName);
        assertEquals(sourceString, customDecodedString, charsetName);
    }

    @ParameterizedTest
    @MethodSource("org.apache.commons.codec.CharsetsTest#getRequiredCharsets()")
    void testRequiredCharset(final Charset charset) throws UnsupportedEncodingException, DecoderException {
        testCharset(charset.name());
    }
}
