package org.apache.commons.codec.net;

import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

/**
 * Verifies how {@link QCodec#decode(String)} handles a {@code null} input.
 */
public class QCodecTest_testDecodeStringWithNull {

    /**
     * Decoding a {@code null} string should simply return {@code null}
     * rather than throwing or producing an empty string.
     */
    @Test
    void testDecodeStringWithNull() throws Exception {
        final QCodec qcodec = new QCodec();

        final String decoded = qcodec.decode((String) null);

        assertNull(decoded, "Decoding a null string should return null");
    }
}
