package org.apache.commons.codec.net;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.apache.commons.codec.DecoderException;
import org.junit.jupiter.api.Test;

public class PercentCodecTest_testDecodeUnsupportedObject {

    @Test
    void testDecodeUnsupportedObject() {
        final PercentCodec percentCodec = new PercentCodec();

        assertThrows(DecoderException.class, () -> percentCodec.decode("test"));
    }
}
