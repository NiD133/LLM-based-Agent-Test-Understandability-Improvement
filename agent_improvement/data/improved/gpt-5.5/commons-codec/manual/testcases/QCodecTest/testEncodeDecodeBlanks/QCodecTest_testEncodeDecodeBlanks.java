package org.apache.commons.codec.net;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class QCodecTest_testEncodeDecodeBlanks {

    private static final String PLAIN_TEXT = "Mind those pesky blanks";
    private static final String ENCODED_WITH_SPACES = "=?UTF-8?Q?Mind those pesky blanks?=";
    private static final String ENCODED_WITH_UNDERSCORES = "=?UTF-8?Q?Mind_those_pesky_blanks?=";
    private static final String ASSERTION_MESSAGE = "Blanks encoding with the Q codec test";

    @Test
    void testEncodeDecodeBlanks() throws Exception {
        final QCodec qcodec = new QCodec();

        qcodec.setEncodeBlanks(false);
        final String spacesPreserved = qcodec.encode(PLAIN_TEXT);
        assertEquals(ENCODED_WITH_SPACES, spacesPreserved, ASSERTION_MESSAGE);

        qcodec.setEncodeBlanks(true);
        final String spacesEncodedAsUnderscores = qcodec.encode(PLAIN_TEXT);
        assertEquals(ENCODED_WITH_UNDERSCORES, spacesEncodedAsUnderscores, ASSERTION_MESSAGE);

        final String decodedSpaces = qcodec.decode(ENCODED_WITH_SPACES);
        assertEquals(PLAIN_TEXT, decodedSpaces, ASSERTION_MESSAGE);

        final String decodedUnderscores = qcodec.decode(ENCODED_WITH_UNDERSCORES);
        assertEquals(PLAIN_TEXT, decodedUnderscores, ASSERTION_MESSAGE);
    }
}
