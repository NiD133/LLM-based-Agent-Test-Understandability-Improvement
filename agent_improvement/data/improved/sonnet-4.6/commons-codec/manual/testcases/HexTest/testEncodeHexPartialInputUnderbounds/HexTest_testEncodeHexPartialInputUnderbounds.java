package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

public class HexTest_testEncodeHexPartialInputUnderbounds {

    /**
     * A negative dataOffset is below the valid array index range, so the
     * internal loop in encodeHex immediately accesses data[-2] and the JVM
     * throws ArrayIndexOutOfBoundsException.
     */
    @Test
    void testEncodeHexPartialInputUnderbounds() {
        final byte[] data = "hello world".getBytes(StandardCharsets.UTF_8);
        assertThrows(ArrayIndexOutOfBoundsException.class,
                () -> Hex.encodeHex(data, /* dataOffset= */ -2, /* dataLen= */ 10, /* toLowerCase= */ true));
    }
}
