package org.apache.commons.codec.net;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.apache.commons.codec.DecoderException;
import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link URLCodec#decode(String)} rejects malformed percent-escapes.
 *
 * <p>A valid percent-escape is the character {@code '%'} followed by exactly two
 * hexadecimal digits (e.g. {@code "%20"}). Each case below violates that rule and
 * is therefore expected to fail with a {@link DecoderException}.</p>
 */
public class URLCodecTest_testDecodeInvalid {

    private final URLCodec urlCodec = new URLCodec();

    @Test
    void testDecodeInvalid() {
        // '%' with no following digits.
        assertThrows(DecoderException.class, () -> urlCodec.decode("%"));

        // '%' followed by only one digit instead of two.
        assertThrows(DecoderException.class, () -> urlCodec.decode("%A"));

        // First character after '%' is not a hex digit.
        assertThrows(DecoderException.class, () -> urlCodec.decode("%WW"));

        // Second character after '%' is not a hex digit.
        assertThrows(DecoderException.class, () -> urlCodec.decode("%0W"));
    }
}
