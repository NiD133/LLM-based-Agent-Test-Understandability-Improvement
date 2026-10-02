package org.apache.commons.codec.net;

import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link BCodec#encode(String, String)} returns {@code null}
 * when given a {@code null} input string, regardless of the requested charset.
 */
public class BCodecTest_testEncodeStringWithNull {

    @Test
    void testEncodeStringWithNull() throws Exception {
        final BCodec bcodec = new BCodec();

        final String encoded = bcodec.encode(null, "charset");

        assertNull(encoded, "Encoding a null string should yield null");
    }
}
