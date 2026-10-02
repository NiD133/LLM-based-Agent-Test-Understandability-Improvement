package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

public class HexTest_testEncodeHexPartialInputUnderbounds {

    @Test
    void testEncodeHexPartialInputUnderbounds() {
        final byte[] data = "hello world".getBytes(StandardCharsets.UTF_8);

        assertThrows(ArrayIndexOutOfBoundsException.class, () -> Hex.encodeHex(data, -2, 10, true));
    }
}
