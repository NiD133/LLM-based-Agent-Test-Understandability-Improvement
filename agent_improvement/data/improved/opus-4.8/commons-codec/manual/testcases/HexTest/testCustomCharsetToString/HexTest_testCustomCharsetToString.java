package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class HexTest_testCustomCharsetToString {

    /**
     * A Hex codec's string representation should mention the charset it uses.
     * A codec created with the no-arg constructor uses the default charset,
     * so its toString() must contain {@link Hex#DEFAULT_CHARSET_NAME}.
     */
    @Test
    void testCustomCharsetToString() {
        final Hex defaultCharsetCodec = new Hex();

        final String description = defaultCharsetCodec.toString();

        assertTrue(description.contains(Hex.DEFAULT_CHARSET_NAME));
    }
}
