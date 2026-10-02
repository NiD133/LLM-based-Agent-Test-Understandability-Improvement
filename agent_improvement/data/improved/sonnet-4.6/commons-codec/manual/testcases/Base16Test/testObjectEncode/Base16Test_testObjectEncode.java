package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

public class Base16Test_testObjectEncode {

    @Test
    void testObjectEncode() {
        final Base16 b16 = new Base16();
        final byte[] inputBytes = "Hello World".getBytes(StandardCharsets.UTF_8);
        final String encoded = new String(b16.encode(inputBytes));
        assertEquals("48656C6C6F20576F726C64", encoded,
                "Base16 encoding of 'Hello World' should produce its uppercase hex representation");
    }
}
