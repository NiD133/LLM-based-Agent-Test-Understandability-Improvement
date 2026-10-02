package org.apache.commons.codec.net;

import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link QCodec#encode(String, String)} returns {@code null}
 * when the input string is {@code null}, regardless of the charset name given.
 */
public class QCodecTest_testEncodeStringWithNull {

    @Test
    void encodingNullStringReturnsNull() throws Exception {
        final QCodec qcodec = new QCodec();

        final String encoded = qcodec.encode(null, "charset");

        assertNull(encoded, "Encoding a null string should yield null");
    }
}
