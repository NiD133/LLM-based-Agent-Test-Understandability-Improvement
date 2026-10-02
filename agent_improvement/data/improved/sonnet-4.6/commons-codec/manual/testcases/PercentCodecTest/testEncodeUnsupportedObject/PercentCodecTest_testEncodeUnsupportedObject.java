package org.apache.commons.codec.net;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.apache.commons.codec.EncoderException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class PercentCodecTest_testEncodeUnsupportedObject {

    /**
     * PercentCodec.encode(Object) only accepts byte[] arguments.
     * Passing any other type (e.g. String) must throw EncoderException
     * rather than silently succeeding or throwing an unchecked exception.
     */
    @Test
    @DisplayName("encode(Object) throws EncoderException when given a non-byte-array argument")
    void testEncodeUnsupportedObject() {
        final PercentCodec percentCodec = new PercentCodec();
        assertThrows(EncoderException.class, () -> percentCodec.encode("test"));
    }
}
