package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

public class HexTest_testGetCharset {

    @Test
    void testGetCharset() {
        assertEquals(StandardCharsets.UTF_8, new Hex(StandardCharsets.UTF_8).getCharset());
    }
}
