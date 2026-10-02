package org.apache.commons.codec.net;

import static org.junit.jupiter.api.Assertions.assertNull;

import org.apache.commons.codec.DecoderException;
import org.junit.jupiter.api.Test;

public class QCodecTest_testDecodeStringWithNull {

    @Test
    void testDecodeStringWithNull() throws DecoderException {
        final QCodec qcodec = new QCodec();
        final String result = qcodec.decode((String) null);
        assertNull(result, "Result should be null");
    }
}
