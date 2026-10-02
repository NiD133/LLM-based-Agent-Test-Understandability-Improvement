package org.apache.commons.codec.net;

import static org.junit.jupiter.api.Assertions.assertNull;

import org.apache.commons.codec.DecoderException;
import org.junit.jupiter.api.Test;

public class URLCodecTest_testDecodeStringWithNull {

    @Test
    void testDecodeStringWithNull() throws Exception {
        final URLCodec urlCodec = new URLCodec();
        final String result = urlCodec.decode((String) null, "charset");
        assertNull(result, "Result should be null");
    }
}
