package org.apache.commons.codec.net;

import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

public class PercentCodecTest_testDecodeNullObject {

    @Test
    void decodeObjectReturnsNullWhenInputIsNull() throws Exception {
        final PercentCodec percentCodec = new PercentCodec();

        assertNull(percentCodec.decode((Object) null));
    }
}
