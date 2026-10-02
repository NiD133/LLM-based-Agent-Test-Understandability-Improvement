package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

public class HexTest_testGetCharset {

    /**
     * Verifies that a Hex codec constructed with an explicit Charset reports
     * that same Charset back through {@link Hex#getCharset()}.
     */
    @Test
    void testGetCharset() {
        final Hex hex = new Hex(StandardCharsets.UTF_8);

        assertEquals(StandardCharsets.UTF_8, hex.getCharset());
    }
}
