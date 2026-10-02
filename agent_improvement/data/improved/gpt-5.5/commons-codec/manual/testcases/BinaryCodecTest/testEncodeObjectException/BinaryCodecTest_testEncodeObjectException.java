package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.apache.commons.codec.EncoderException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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

    @Test
    void testEncodeObjectException() {
        assertThrows(EncoderException.class, () -> instance.encode(""));
    }
}
