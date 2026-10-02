package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.UnsupportedEncodingException;

import org.apache.commons.codec.DecoderException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class HexTest_testCustomCharset {

    private static final boolean LOG = false;

    private boolean charsetSanityCheck(final String name) {
        final String source = "the quick brown dog jumped over the lazy fox";
        try {
            final byte[] bytes = source.getBytes(name);
            final String roundTripped = new String(bytes, name);
            final boolean canRoundTrip = source.equals(roundTripped);
            if (!canRoundTrip) {
                log("FAILED charsetSanityCheck=Interesting Java charset oddity: Roundtrip failed for " + name);
            }
            return canRoundTrip;
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

    private void testCharset(final String name, final String parent) throws UnsupportedEncodingException, DecoderException {
        if (!charsetSanityCheck(name)) {
            return;
        }
        log(parent + "=" + name);

        final String sourceString = "Hello World";
        final byte[] sourceBytes = sourceString.getBytes(name);
        final Hex customCodec = new Hex(name);

        final byte[] customEncodedBytes = customCodec.encode(sourceBytes);
        String expectedHexString = Hex.encodeHexString(sourceBytes);
        final byte[] expectedEncodedBytes = expectedHexString.getBytes(name);
        assertArrayEquals(expectedEncodedBytes, customEncodedBytes);

        String encodedString = new String(customEncodedBytes, name);
        assertEquals(expectedHexString, encodedString, name);

        final Hex utf8Codec = new Hex();
        expectedHexString = "48656c6c6f20576f726c64";
        final byte[] decodedUtf8Bytes = (byte[]) utf8Codec.decode(expectedHexString);
        encodedString = new String(decodedUtf8Bytes, utf8Codec.getCharset());
        assertEquals(sourceString, encodedString, name);

        final byte[] decodedCustomBytes = customCodec.decode(customEncodedBytes);
        encodedString = new String(decodedCustomBytes, name);
        assertEquals(sourceString, encodedString, name);
    }

    @ParameterizedTest
    @MethodSource("org.apache.commons.codec.CharsetsTest#getAvailableCharsetNames()")
    void testCustomCharset(final String name) throws UnsupportedEncodingException, DecoderException {
        testCharset(name, "testCustomCharset");
    }
}
