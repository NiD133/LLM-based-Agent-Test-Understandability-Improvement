package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.apache.commons.codec.EncoderException;
import org.junit.jupiter.api.Test;

public class Base58Test_testObjectEncodeWithInvalidParameter {

    @Test
    void testObjectEncodeWithInvalidParameter() {
        // Base58.encode(Object) only accepts byte[]; passing a String must throw EncoderException.
        assertThrows(EncoderException.class, () -> new Base58().encode("Yadayadayada"));
    }
}
