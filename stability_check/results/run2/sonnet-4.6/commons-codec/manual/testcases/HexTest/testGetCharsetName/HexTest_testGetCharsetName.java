package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

public class HexTest_testGetCharsetName {

    @Test
    void testGetCharsetName() {
        // A Hex codec constructed with a Charset should report that charset's name via getCharsetName()
        String expectedName = StandardCharsets.UTF_8.name();
        Hex hex = new Hex(StandardCharsets.UTF_8);
        assertEquals(expectedName, hex.getCharsetName());
    }
}
