package org.apache.commons.codec.net;

import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

/**
 * Verifies the null-handling contract of {@link PercentCodec#encode(Object)}.
 */
public class PercentCodecTest_testEncodeNullObject {

    /**
     * Encoding a {@code null} object should return {@code null} rather than
     * throwing, as documented by {@link PercentCodec#encode(Object)}.
     */
    @Test
    void encodeNullObjectReturnsNull() throws Exception {
        final PercentCodec percentCodec = new PercentCodec();

        final Object encoded = percentCodec.encode((Object) null);

        assertNull(encoded, "Encoding a null object must yield null");
    }
}
