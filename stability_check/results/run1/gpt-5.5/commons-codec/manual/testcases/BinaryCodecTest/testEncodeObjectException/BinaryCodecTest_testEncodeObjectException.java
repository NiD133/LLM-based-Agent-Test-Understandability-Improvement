package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.apache.commons.codec.EncoderException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BinaryCodecTest_testEncodeObjectException {

    private BinaryCodec binaryCodec;

    @BeforeEach
    void setUp() {
        binaryCodec = new BinaryCodec();
    }

    @Test
    void testEncodeObjectException() {
        assertThrows(EncoderException.class, () -> binaryCodec.encode(""));
    }
}
