package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.apache.commons.codec.DecoderException;
import org.junit.jupiter.api.Test;

public class HexTest_testDecodeClassCastException {

    /**
     * {@link Hex#decode(Object)} dispatches on the runtime type of its argument.
     * An {@code int[]} matches none of the supported types (String, byte[],
     * ByteBuffer), so the final fallback cast to {@code char[]} fails. That
     * {@link ClassCastException} must be wrapped and surfaced as a
     * {@link DecoderException}.
     */
    @Test
    void testDecodeClassCastException() {
        final Object unsupportedInput = new int[] { 65 };

        assertThrows(DecoderException.class, () -> new Hex().decode(unsupportedInput));
    }
}
