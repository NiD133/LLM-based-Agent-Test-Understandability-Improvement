package org.apache.commons.codec.net;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

/**
 * Verifies that QCodec correctly handles the optional transformation of space
 * characters during Q-encoding. RFC 1522 allows blanks to be left as literal
 * spaces or encoded as underscores; this test exercises both modes and confirms
 * that decoding restores the original text regardless of which form was used.
 */
public class QCodecTest_testEncodeDecodeBlanks {

    @Test
    void testEncodeDecodeBlanks() throws Exception {
        final String plainText = "Mind those pesky blanks";

        // When encodeBlanks is false, spaces are kept as literal spaces inside the encoded word.
        final String encodedWithLiteralSpaces = "=?UTF-8?Q?Mind those pesky blanks?=";

        // When encodeBlanks is true, spaces are replaced by underscores per the Q-encoding convention.
        final String encodedWithUnderscores = "=?UTF-8?Q?Mind_those_pesky_blanks?=";

        final QCodec qcodec = new QCodec();

        // --- Encoding ---

        qcodec.setEncodeBlanks(false);
        String encodedResult = qcodec.encode(plainText);
        assertEquals(encodedWithLiteralSpaces, encodedResult, "Blanks encoding with the Q codec test");

        qcodec.setEncodeBlanks(true);
        encodedResult = qcodec.encode(plainText);
        assertEquals(encodedWithUnderscores, encodedResult, "Blanks encoding with the Q codec test");

        // --- Decoding ---

        // Both encoded forms must decode back to the original plain text.
        String decodedResult = qcodec.decode(encodedWithLiteralSpaces);
        assertEquals(plainText, decodedResult, "Blanks decoding with the Q codec test");

        decodedResult = qcodec.decode(encodedWithUnderscores);
        assertEquals(plainText, decodedResult, "Blanks decoding with the Q codec test");
    }
}
