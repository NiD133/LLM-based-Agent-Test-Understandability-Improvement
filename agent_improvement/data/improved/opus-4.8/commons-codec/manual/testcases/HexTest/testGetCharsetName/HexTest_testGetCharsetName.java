package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link Hex#getCharsetName()} reports the name of the charset the codec was created with.
 */
public class HexTest_testGetCharsetName {

    @Test
    void testGetCharsetName() {
        final Hex hex = new Hex(StandardCharsets.UTF_8);

        assertEquals(StandardCharsets.UTF_8.name(), hex.getCharsetName());
    }
}
