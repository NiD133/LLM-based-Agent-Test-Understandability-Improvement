package org.apache.commons.codec.net;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.apache.commons.codec.EncoderException;
import org.junit.jupiter.api.Test;

public class PercentCodecTest_testEncodeUnsupportedObject {

    @Test
    void testEncodeUnsupportedObject() {
        final PercentCodec percentCodec = new PercentCodec();
        final String unsupportedInput = "test";

        assertThrows(EncoderException.class, () -> percentCodec.encode(unsupportedInput));
    }
}
