package org.apache.commons.codec.net;

import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

public class BCodecTest_testEncodeDecodeNull {

    @Test
    void testEncodeDecodeNull() throws Exception {
        final BCodec bcodec = new BCodec();

        assertNull(bcodec.encode((String) null), "Null string B encoding test");
        assertNull(bcodec.decode((String) null), "Null string B decoding test");
    }
}
