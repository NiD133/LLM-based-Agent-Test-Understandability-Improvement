package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BinaryCodecTest_testEncodeObjectNull {

    private BinaryCodec instance;

    @BeforeEach
    void setUp() throws Exception {
        this.instance = new BinaryCodec();
    }

    @AfterEach
    void tearDown() throws Exception {
        this.instance = null;
    }

    @Test
    void testEncodeObjectNull() throws Exception {
        final Object emptyByteArray = new byte[0];

        final char[] encoded = (char[]) instance.encode(emptyByteArray);

        assertEquals(0, encoded.length);
    }
}
