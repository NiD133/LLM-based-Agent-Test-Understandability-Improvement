package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.apache.commons.codec.EncoderException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests that BinaryCodec.encode(Object) rejects non-byte[] arguments with EncoderException.
 */
public class BinaryCodecTest_testEncodeObjectException {

    private BinaryCodec instance;

    @BeforeEach
    void setUp() {
        instance = new BinaryCodec();
    }

    @AfterEach
    void tearDown() {
        instance = null;
    }

    /**
     * encode(Object) must throw EncoderException when the argument is not a byte[].
     * Passing a String (an unsupported type) should trigger this guard.
     */
    @Test
    void testEncodeObjectException() {
        assertThrows(EncoderException.class, () -> instance.encode(""));
    }
}
