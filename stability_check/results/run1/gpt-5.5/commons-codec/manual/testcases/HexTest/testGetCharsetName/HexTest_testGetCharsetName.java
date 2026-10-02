package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

public class HexTest_testGetCharsetName {

    @Test
    void testGetCharsetName() {
        assertEquals(StandardCharsets.UTF_8.name(), new Hex(StandardCharsets.UTF_8).getCharsetName());
    }
}
