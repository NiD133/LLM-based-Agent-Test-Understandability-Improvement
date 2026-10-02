package org.apache.commons.codec.net;

import static org.junit.jupiter.api.Assertions.assertNull;

import org.apache.commons.codec.EncoderException;
import org.junit.jupiter.api.Test;

public class QCodecTest_testEncodeStringWithNull {

    @Test
    void testEncodeStringWithNull() throws EncoderException {
        final QCodec qcodec = new QCodec();
        final String result = qcodec.encode((String) null, "charset");
        assertNull(result, "Result should be null");
    }
}
