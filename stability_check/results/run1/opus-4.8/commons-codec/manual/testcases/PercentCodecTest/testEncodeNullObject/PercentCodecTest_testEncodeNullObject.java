package org.apache.commons.codec.net;

import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

/**
 * Verifies {@link PercentCodec#encode(Object)} handling of a {@code null} input.
 */
public class PercentCodecTest_testEncodeNullObject {

    /**
     * Encoding a {@code null} Object should return {@code null} rather than
     * throwing or producing an empty result.
     */
    @Test
    void testEncodeNullObject() throws Exception {
        final PercentCodec percentCodec = new PercentCodec();

        final Object encoded = percentCodec.encode((Object) null);

        assertNull(encoded, "Encoding a null Object must return null");
    }
}
