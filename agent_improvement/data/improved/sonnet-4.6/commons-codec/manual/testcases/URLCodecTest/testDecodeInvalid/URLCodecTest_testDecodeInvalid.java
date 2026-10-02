package org.apache.commons.codec.net;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.apache.commons.codec.DecoderException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class URLCodecTest_testDecodeInvalid {

    @Test
    @DisplayName("decode() throws DecoderException for malformed percent-encoded sequences")
    void testDecodeInvalid() throws Exception {
        final URLCodec urlCodec = new URLCodec();

        // "%" alone — escape char with no following hex digits
        assertThrows(DecoderException.class, () -> urlCodec.decode("%"));

        // "%A" — escape char followed by only one hex digit instead of two
        assertThrows(DecoderException.class, () -> urlCodec.decode("%A"));

        // "%WW" — 'W' is not a valid hex digit (bad 1st char after %)
        assertThrows(DecoderException.class, () -> urlCodec.decode("%WW"));

        // "%0W" — '0' is valid but 'W' is not (bad 2nd char after %)
        assertThrows(DecoderException.class, () -> urlCodec.decode("%0W"));
    }
}
