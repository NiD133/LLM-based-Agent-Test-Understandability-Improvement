package org.apache.commons.codec.net;

import static org.junit.jupiter.api.Assertions.assertNull;

import org.apache.commons.codec.DecoderException;
import org.junit.jupiter.api.Test;

public class BCodecTest_testDecodeStringWithNull {

    @Test
    void testDecodeStringWithNull() throws DecoderException {
        final BCodec bcodec = new BCodec();
        final String result = bcodec.decode((String) null);
        assertNull(result, "Result should be null");
    }
}
