package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertThrows;
import org.apache.commons.codec.DecoderException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BinaryCodecTest_testDecodeObjectException {

    BinaryCodec instance;

    @BeforeEach
    void setUp() throws Exception {
        this.instance = new BinaryCodec();
    }

    @AfterEach
    void tearDown() throws Exception {
        this.instance = null;
    }

    /**
     * Tests that decode(Object) throws DecoderException when given an unsupported type.
     */
    @Test
    void testDecodeObjectException() {
        assertThrows(DecoderException.class, () -> this.instance.decode(new Object()));
    }
}
