package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

public class HexTest_testGetCharsetName {

    /**
     * Verifies that a {@link Hex} created with a specific {@link java.nio.charset.Charset}
     * reports that charset's name via {@link Hex#getCharsetName()}.
     */
    @Test
    void testGetCharsetName() {
        final Hex hex = new Hex(StandardCharsets.UTF_8);

        assertEquals(StandardCharsets.UTF_8.name(), hex.getCharsetName());
    }
}
