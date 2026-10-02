package org.apache.commons.codec.net;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.nio.charset.UnsupportedCharsetException;

import org.junit.jupiter.api.Test;

/**
 * Verifies that constructing a {@link BCodec} with an unknown charset name fails fast.
 */
public class BCodecTest_testInvalidEncoding {

    /**
     * The {@code BCodec(String)} constructor resolves the charset name via
     * {@code Charset.forName}, so an unrecognized name must raise an
     * {@link UnsupportedCharsetException}.
     */
    @Test
    void testInvalidEncoding() {
        final String unknownCharsetName = "NONSENSE";

        assertThrows(UnsupportedCharsetException.class, () -> new BCodec(unknownCharsetName));
    }
}
