package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link Hex#getCharsetName()}.
 */
public class HexTest_testGetCharsetName {

    /**
     * A {@link Hex} created from a {@link java.nio.charset.Charset} should report
     * that charset's canonical name via {@link Hex#getCharsetName()}.
     */
    @Test
    void testGetCharsetName() {
        final Hex hex = new Hex(StandardCharsets.UTF_8);

        final String expectedCharsetName = StandardCharsets.UTF_8.name();

        assertEquals(expectedCharsetName, hex.getCharsetName());
    }
}
