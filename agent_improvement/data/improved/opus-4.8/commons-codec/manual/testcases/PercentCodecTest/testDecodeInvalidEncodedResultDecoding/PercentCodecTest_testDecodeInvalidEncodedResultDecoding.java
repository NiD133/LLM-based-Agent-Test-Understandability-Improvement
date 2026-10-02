package org.apache.commons.codec.net;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import org.apache.commons.codec.DecoderException;
import org.junit.jupiter.api.Test;

/**
 * Verifies that decoding a truncated Percent-Encoded byte array fails gracefully.
 *
 * <p>When a "%" escape character is not followed by its two expected hex digits, the
 * decoder reads past the end of the array. {@link PercentCodec#decode(byte[])} is
 * expected to catch the resulting {@link ArrayIndexOutOfBoundsException} and re-throw
 * it wrapped inside a {@link DecoderException}.</p>
 */
public class PercentCodecTest_testDecodeInvalidEncodedResultDecoding {

    @Test
    void testDecodeInvalidEncodedResultDecoding() throws Exception {
        // The Greek letters alpha and beta are non-ASCII, so they get Percent-Encoded.
        final String nonAsciiInput = "αβ";
        final PercentCodec percentCodec = new PercentCodec();

        final byte[] encoded = percentCodec.encode(nonAsciiInput.getBytes(StandardCharsets.UTF_8));

        // Drop the final byte so the last "%" escape sequence is missing a hex digit,
        // making the encoded input invalid.
        final byte[] truncatedEncoded = Arrays.copyOf(encoded, encoded.length - 1);

        try {
            percentCodec.decode(truncatedEncoded);
        } catch (final Exception thrown) {
            // The decoder must report the failure as a DecoderException that wraps the
            // underlying ArrayIndexOutOfBoundsException as its cause.
            assertTrue(thrown instanceof DecoderException
                    && thrown.getCause() instanceof ArrayIndexOutOfBoundsException);
        }
    }
}
