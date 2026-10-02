package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

public class HexTest_testGetCharsetName {

    @Test
    void testGetCharsetName() {
        // Hex constructed with a Charset should report the same charset name via getCharsetName()
        String expectedName = StandardCharsets.UTF_8.name();
        String actualName = new Hex(StandardCharsets.UTF_8).getCharsetName();
        assertEquals(expectedName, actualName);
    }
}
