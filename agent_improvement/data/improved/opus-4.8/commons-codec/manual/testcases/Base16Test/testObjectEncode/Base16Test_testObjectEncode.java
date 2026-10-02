package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link Base16#encode(byte[])} produces the RFC 4648 upper-case
 * hexadecimal representation of its input.
 */
public class Base16Test_testObjectEncode {

    @Test
    void testObjectEncode() {
        final Base16 base16 = new Base16();

        final byte[] inputBytes = "Hello World".getBytes(StandardCharsets.UTF_8);
        final String encoded = new String(base16.encode(inputBytes));

        assertEquals("48656C6C6F20576F726C64", encoded);
    }
}
