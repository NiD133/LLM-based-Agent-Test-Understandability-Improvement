package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.apache.commons.codec.EncoderException;
import org.junit.jupiter.api.Test;

public class Base16Test_testObjectEncodeWithInvalidParameter {

    /**
     * Base16.encode(Object) only accepts byte[] arguments.
     * Passing any other type (e.g. String) must throw EncoderException.
     */
    @Test
    void testObjectEncodeWithInvalidParameter() {
        assertThrows(EncoderException.class, () -> new Base16().encode("Yadayadayada"));
    }
}
