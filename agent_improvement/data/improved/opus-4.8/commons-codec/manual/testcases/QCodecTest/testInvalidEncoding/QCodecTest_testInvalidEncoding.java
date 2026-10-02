package org.apache.commons.codec.net;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.nio.charset.UnsupportedCharsetException;

import org.junit.jupiter.api.Test;

/**
 * Verifies that constructing a {@link QCodec} with an unknown charset name fails fast.
 */
public class QCodecTest_testInvalidEncoding {

    /**
     * The {@code QCodec(String)} constructor resolves the charset via {@code Charset.forName},
     * so an unrecognized charset name must raise an {@link UnsupportedCharsetException}.
     */
    @Test
    void testInvalidEncoding() {
        final String unknownCharsetName = "NONSENSE";

        assertThrows(UnsupportedCharsetException.class, () -> new QCodec(unknownCharsetName));
    }
}
