package org.apache.commons.codec.net;

import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

public class URLCodecTest_testDecodeStringWithNull {

    @Test
    void testDecodeStringWithNull() throws Exception {
        final URLCodec urlCodec = new URLCodec();
        final String decodedValue = urlCodec.decode(null, "charset");

        assertNull(decodedValue, "Decoding a null string should return null");
    }
}
