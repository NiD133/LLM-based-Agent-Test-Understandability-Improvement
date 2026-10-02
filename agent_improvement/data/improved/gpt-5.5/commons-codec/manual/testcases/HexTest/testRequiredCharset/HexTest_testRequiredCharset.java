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
    private static final String CHARSET_ROUND_TRIP_SAMPLE = "the quick brown dog jumped over the lazy fox";
    private static final String SOURCE_STRING = "Hello World";
    private static final String SOURCE_STRING_UTF8_HEX = "48656c6c6f20576f726c64";

    private boolean charsetCanRoundTripSampleText(final String charsetName) {
        try {
            final byte[] bytes = CHARSET_ROUND_TRIP_SAMPLE.getBytes(charsetName);
            final String decoded = new String(bytes, charsetName);
            final boolean canRoundTrip = CHARSET_ROUND_TRIP_SAMPLE.equals(decoded);
            if (!canRoundTrip) {
                log("FAILED charsetSanityCheck=Interesting Java charset oddity: Roundtrip failed for " + charsetName);
            }
            return canRoundTrip;
        } catch (final UnsupportedEncodingException | UnsupportedOperationException e) {
            if (LOG) {
                log("FAILED charsetSanityCheck=" + charsetName + ", e=" + e);
                log(e);
            }
            return false;
        }
    }

    private void log(final String message) {
        if (LOG) {
            System.out.println(message);
            System.out.flush();
        }
    }

    private void log(final Throwable throwable) {
        if (LOG) {
            throwable.printStackTrace(System.out);
            System.out.flush();
        }
    }

    private void testCharset(final String charsetName, final String parent) throws UnsupportedEncodingException, DecoderException {
        if (!charsetCanRoundTripSampleText(charsetName)) {
            return;
        }

        log(parent + "=" + charsetName);

        final Hex customCodec = new Hex(charsetName);
        final byte[] sourceBytes = SOURCE_STRING.getBytes(charsetName);

        final byte[] actualEncodedBytes = customCodec.encode(sourceBytes);
        final String expectedHexString = Hex.encodeHexString(sourceBytes);
        final byte[] expectedHexStringBytes = expectedHexString.getBytes(charsetName);
        assertArrayEquals(expectedHexStringBytes, actualEncodedBytes);

        final String encodedString = new String(actualEncodedBytes, charsetName);
        assertEquals(expectedHexString, encodedString, charsetName);

        final Hex utf8Codec = new Hex();
        final byte[] decodedUtf8Bytes = (byte[]) utf8Codec.decode(SOURCE_STRING_UTF8_HEX);
        final String decodedUtf8String = new String(decodedUtf8Bytes, utf8Codec.getCharset());
        assertEquals(SOURCE_STRING, decodedUtf8String, charsetName);

        final byte[] decodedCustomBytes = customCodec.decode(actualEncodedBytes);
        final String decodedCustomString = new String(decodedCustomBytes, charsetName);
        assertEquals(SOURCE_STRING, decodedCustomString, charsetName);
    }

    @ParameterizedTest
    @MethodSource("org.apache.commons.codec.CharsetsTest#getRequiredCharsets()")
    void testRequiredCharset(final Charset charset) throws UnsupportedEncodingException, DecoderException {
        testCharset(charset.name(), "testRequiredCharset");
    }
}
