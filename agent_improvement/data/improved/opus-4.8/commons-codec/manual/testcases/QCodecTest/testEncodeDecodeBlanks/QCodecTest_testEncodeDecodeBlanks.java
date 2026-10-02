package org.apache.commons.codec.net;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Verifies how {@link QCodec} treats blank (space) characters when the
 * {@code encodeBlanks} flag is toggled.
 *
 * <p>The Q codec offers an optional transformation in which space characters are
 * represented as underscores ({@code _}) instead of being left as literal spaces.
 * This test exercises both settings for encoding and confirms that decoding
 * recovers the original text in either representation.</p>
 */
public class QCodecTest_testEncodeDecodeBlanks {

    /** Plain text containing the blanks we care about. */
    private static final String PLAIN_TEXT = "Mind those pesky blanks";

    /** Expected encoding when blanks are left as literal spaces. */
    private static final String ENCODED_WITH_SPACES = "=?UTF-8?Q?Mind those pesky blanks?=";

    /** Expected encoding when blanks are transformed into underscores. */
    private static final String ENCODED_WITH_UNDERSCORES = "=?UTF-8?Q?Mind_those_pesky_blanks?=";

    @Test
    void testEncodeDecodeBlanks() throws Exception {
        final QCodec qcodec = new QCodec();

        // When blank transformation is disabled, spaces stay as spaces.
        qcodec.setEncodeBlanks(false);
        assertEquals(ENCODED_WITH_SPACES, qcodec.encode(PLAIN_TEXT),
                "Spaces should be preserved when encodeBlanks is false");

        // When blank transformation is enabled, spaces become underscores.
        qcodec.setEncodeBlanks(true);
        assertEquals(ENCODED_WITH_UNDERSCORES, qcodec.encode(PLAIN_TEXT),
                "Spaces should become underscores when encodeBlanks is true");

        // Decoding must recover the original text from either representation.
        assertEquals(PLAIN_TEXT, qcodec.decode(ENCODED_WITH_SPACES),
                "Space-encoded form should decode back to the original text");
        assertEquals(PLAIN_TEXT, qcodec.decode(ENCODED_WITH_UNDERSCORES),
                "Underscore-encoded form should decode back to the original text");
    }
}
