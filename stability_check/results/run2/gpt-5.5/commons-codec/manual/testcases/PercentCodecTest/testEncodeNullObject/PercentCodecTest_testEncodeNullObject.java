package org.apache.commons.codec.net;

import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

public class PercentCodecTest_testEncodeNullObject {

    @Test
    void testEncodeNullObject() throws Exception {
        final PercentCodec percentCodec = new PercentCodec();

        assertNull(percentCodec.encode((Object) null));
    }
}
