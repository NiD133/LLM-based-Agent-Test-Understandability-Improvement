package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.apache.commons.codec.DecoderException;
import org.junit.jupiter.api.Test;

public class Base58Test_testObjectDecodeWithInvalidParameter {

    @Test
    void testObjectDecodeWithInvalidParameter() {
        // Base58.decode(Object) must reject non-String/non-byte[] arguments
        assertThrows(DecoderException.class, () -> new Base58().decode(Integer.valueOf(5)));
    }
}
