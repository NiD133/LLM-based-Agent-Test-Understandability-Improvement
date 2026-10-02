package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.apache.commons.codec.EncoderException;
import org.junit.jupiter.api.Test;

/**
 * Tests that {@link Hex#encode(Object)} rejects an argument whose runtime type
 * cannot be encoded.
 */
public class HexTest_testEncodeClassCastException {

    /**
     * {@link Hex#encode(Object)} only supports {@code String}, {@code ByteBuffer}
     * and {@code byte[]} inputs. Passing an {@code int[]} must fail with an
     * {@link EncoderException} (wrapping the underlying {@code ClassCastException})
     * rather than letting the cast error escape.
     */
    @Test
    void testEncodeClassCastException() {
        final Hex hex = new Hex();
        final int[] unsupportedInput = { 65 };

        assertThrows(EncoderException.class, () -> hex.encode(unsupportedInput));
    }
}
