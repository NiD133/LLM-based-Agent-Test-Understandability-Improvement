package org.apache.commons.codec.net;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.EncoderException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Tests that URLCodec raises the correct exceptions when constructed with an unsupported charset name.
 */
public class URLCodecTest_testInvalidEncoding {

    @Test
    @DisplayName("encode and decode throw typed exceptions when the codec charset is invalid")
    void testInvalidEncoding() {
        final URLCodec urlCodec = new URLCodec("NONSENSE");
        final String plain = "Hello there!";

        assertThrows(EncoderException.class, () -> urlCodec.encode(plain),
                "encode() must throw EncoderException for an unsupported charset");
        assertThrows(DecoderException.class, () -> urlCodec.decode(plain),
                "decode() must throw DecoderException for an unsupported charset");
    }
}
