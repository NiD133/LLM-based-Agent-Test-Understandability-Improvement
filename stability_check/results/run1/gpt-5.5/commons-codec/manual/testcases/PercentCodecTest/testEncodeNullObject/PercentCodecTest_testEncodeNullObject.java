package org.apache.commons.codec.net;

import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

public class PercentCodecTest_testEncodeNullObject {

    @Test
    void testEncodeNullObject() throws Exception {
        final PercentCodec percentCodec = new PercentCodec();

        final Object encodedNull = percentCodec.encode((Object) null);

        assertNull(encodedNull);
    }
}
