package org.apache.commons.codec.net;

import static org.junit.jupiter.api.Assertions.assertNull;

import org.apache.commons.codec.EncoderException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class PercentCodecTest_testEncodeNullObject {

    // PercentCodec.encode(Object) must return null when given a null input,
    // matching the contract defined in the BinaryEncoder interface.
    @Test
    @DisplayName("encode(null) returns null without throwing an exception")
    void testEncodeNullObject() throws EncoderException {
        final PercentCodec percentCodec = new PercentCodec();
        assertNull(percentCodec.encode((Object) null));
    }
}
