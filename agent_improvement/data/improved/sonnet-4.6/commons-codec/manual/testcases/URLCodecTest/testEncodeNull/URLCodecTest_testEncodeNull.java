package org.apache.commons.codec.net;

import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.Test;

public class URLCodecTest_testEncodeNull {

    @Test
    void testEncodeNull() throws Exception {
        final URLCodec urlCodec = new URLCodec();
        final byte[] nullByteArray = null;
        final byte[] encoded = urlCodec.encode(nullByteArray);
        assertNull(encoded, "Encoding a null byte array should return null");
    }
}
