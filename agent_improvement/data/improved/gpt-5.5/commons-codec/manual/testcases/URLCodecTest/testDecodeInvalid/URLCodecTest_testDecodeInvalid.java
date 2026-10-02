package org.apache.commons.codec.net;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.apache.commons.codec.DecoderException;
import org.junit.jupiter.api.Test;

public class URLCodecTest_testDecodeInvalid {

    private static final String INCOMPLETE_ESCAPE_SEQUENCE = "%";
    private static final String ESCAPE_SEQUENCE_MISSING_SECOND_HEX_DIGIT = "%A";
    private static final String ESCAPE_SEQUENCE_WITH_INVALID_FIRST_HEX_DIGIT = "%WW";
    private static final String ESCAPE_SEQUENCE_WITH_INVALID_SECOND_HEX_DIGIT = "%0W";

    private void assertDecodeFails(final URLCodec urlCodec, final String encodedValue) {
        assertThrows(DecoderException.class, () -> urlCodec.decode(encodedValue));
    }

    private void validateState(final URLCodec urlCodec) {
        // no tests for now.
    }

    @Test
    void testDecodeInvalid() throws Exception {
        final URLCodec urlCodec = new URLCodec();

        assertDecodeFails(urlCodec, INCOMPLETE_ESCAPE_SEQUENCE);
        assertDecodeFails(urlCodec, ESCAPE_SEQUENCE_MISSING_SECOND_HEX_DIGIT);
        assertDecodeFails(urlCodec, ESCAPE_SEQUENCE_WITH_INVALID_FIRST_HEX_DIGIT);
        assertDecodeFails(urlCodec, ESCAPE_SEQUENCE_WITH_INVALID_SECOND_HEX_DIGIT);

        validateState(urlCodec);
    }
}
