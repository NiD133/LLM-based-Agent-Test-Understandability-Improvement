package org.apache.commons.codec.net;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.apache.commons.codec.DecoderException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class PercentCodecTest_testDecodeUnsupportedObject {

    @Test
    @DisplayName("decode(Object) throws DecoderException when given a non-byte-array argument")
    void testDecodeUnsupportedObject() {
        // PercentCodec.decode(Object) only accepts byte[]; passing a String must be rejected
        final PercentCodec percentCodec = new PercentCodec();
        assertThrows(DecoderException.class, () -> percentCodec.decode("test"));
    }
}
