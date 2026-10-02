package org.apache.commons.codec.net;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class BCodecTest_testBasicEncodeDecode {

    @Test
    void testBasicEncodeDecode() throws Exception {
        final BCodec bcodec = new BCodec();
        final String plain = "Hello there";
        final String encoded = bcodec.encode(plain);

        assertEquals("=?UTF-8?B?SGVsbG8gdGhlcmU=?=", encoded, "Basic B encoding test");
        assertEquals(plain, bcodec.decode(encoded), "Basic B decoding test");
    }
}
