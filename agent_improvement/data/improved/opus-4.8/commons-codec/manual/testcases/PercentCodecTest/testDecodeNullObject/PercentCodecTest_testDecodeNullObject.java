package org.apache.commons.codec.net;

import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link PercentCodec#decode(Object)} returns {@code null}
 * when given a {@code null} input, rather than throwing an exception.
 */
public class PercentCodecTest_testDecodeNullObject {

    @Test
    void decodeNullObjectReturnsNull() throws Exception {
        final PercentCodec percentCodec = new PercentCodec();

        final Object decoded = percentCodec.decode((Object) null);

        assertNull(decoded, "Decoding a null Object should return null");
    }
}
