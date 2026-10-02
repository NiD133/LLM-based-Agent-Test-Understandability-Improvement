package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import org.apache.commons.codec.DecoderException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Verifies that {@link Hex} encodes and decodes correctly for every charset that
 * the JDK is required to support.
 */
public class HexTest_testRequiredCharset {

    /** When {@code true}, diagnostic messages are printed to {@code System.out}. */
    private static final boolean LOG = false;

    /**
     * Confirms that the named charset can round-trip a sample string
     * ({@code String} -> bytes -> {@code String}) without loss.
     *
     * <p>Some JDK charsets are not symmetric for arbitrary text; for those the
     * full encode/decode assertions below would be meaningless, so the caller
     * skips them when this check returns {@code false}.</p>
     *
     * @param charsetName the charset to validate.
     * @return {@code true} if the round-trip preserves the sample string.
     */
    private boolean charsetSanityCheck(final String charsetName) {
        final String source = "the quick brown dog jumped over the lazy fox";
        try {
            final byte[] bytes = source.getBytes(charsetName);
            final String roundTripped = new String(bytes, charsetName);
            final boolean isLossless = source.equals(roundTripped);
            if (!isLossless) {
                log("FAILED charsetSanityCheck=Interesting Java charset oddity: Roundtrip failed for " + charsetName);
            }
            return isLossless;
        } catch (final UnsupportedEncodingException | UnsupportedOperationException e) {
            if (LOG) {
                log("FAILED charsetSanityCheck=" + charsetName + ", e=" + e);
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
     * Exercises a single charset against {@link Hex}:
     * <ol>
     *   <li>encoding "Hello World" with a charset-aware {@code Hex} matches the
     *       default lower-case hex string re-encoded in that charset;</li>
     *   <li>the encoded bytes, read back as a {@code String}, equal that hex string;</li>
     *   <li>decoding restores the original "Hello World".</li>
     * </ol>
     *
     * @param charsetName the charset under test.
     * @param parent      label of the calling test, used only for logging.
     */
    private void testCharset(final String charsetName, final String parent)
            throws UnsupportedEncodingException, DecoderException {
        if (!charsetSanityCheck(charsetName)) {
            return;
        }
        log(parent + "=" + charsetName);

        final Hex charsetCodec = new Hex(charsetName);
        final String sourceString = "Hello World";
        final byte[] sourceBytes = sourceString.getBytes(charsetName);

        // Encoding via the charset-aware codec must equal the default hex string,
        // re-expressed as bytes in the same charset.
        final byte[] actualEncodedBytes = charsetCodec.encode(sourceBytes);
        final String expectedHexString = Hex.encodeHexString(sourceBytes);
        final byte[] expectedHexStringBytes = expectedHexString.getBytes(charsetName);
        assertArrayEquals(expectedHexStringBytes, actualEncodedBytes);

        // The encoded bytes, decoded as text, must read back as the hex string.
        assertEquals(expectedHexString, new String(actualEncodedBytes, charsetName), charsetName);

        // Sanity check: the default UTF-8 codec decodes the known hex of "Hello World".
        final Hex utf8Codec = new Hex();
        final byte[] decodedUtf8Bytes = (byte[]) utf8Codec.decode("48656c6c6f20576f726c64");
        assertEquals(sourceString, new String(decodedUtf8Bytes, utf8Codec.getCharset()), charsetName);

        // Actual check: decoding the charset-encoded bytes restores the source.
        final byte[] decodedCustomBytes = charsetCodec.decode(actualEncodedBytes);
        assertEquals(sourceString, new String(decodedCustomBytes, charsetName), charsetName);
    }

    @ParameterizedTest
    @MethodSource("org.apache.commons.codec.CharsetsTest#getRequiredCharsets()")
    void testRequiredCharset(final Charset charset) throws UnsupportedEncodingException, DecoderException {
        testCharset(charset.name(), "testRequiredCharset");
    }
}
