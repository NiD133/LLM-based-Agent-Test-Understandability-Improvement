package org.apache.commons.codec.net;

import static org.junit.jupiter.api.Assertions.assertNull;

import org.apache.commons.codec.EncoderException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class PercentCodecTest_testEncodeNullObject {

    /**
     * Verifies that encoding a null Object input returns null without throwing an exception,
     * confirming the null-safe contract of PercentCodec.encode(Object).
     */
    @Test
    @DisplayName("encode(Object) returns null when given a null argument")
    void testEncodeNullObject() throws Exception {
        final PercentCodec percentCodec = new PercentCodec();
        assertNull(percentCodec.encode((Object) null));
    }
}
