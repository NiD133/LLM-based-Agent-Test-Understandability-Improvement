package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.apache.commons.codec.EncoderException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BinaryCodecTest_testEncodeObjectNull {

    BinaryCodec instance;

    @BeforeEach
    void setUp() throws Exception {
        this.instance = new BinaryCodec();
    }

    @AfterEach
    void tearDown() throws Exception {
        this.instance = null;
    }

    /*
     * Tests for Object encode(Object):
     * encoding an empty byte array should produce an empty char array.
     */
    @Test
    void testEncodeObjectNull() throws Exception {
        final Object emptyByteArray = new byte[0];
        assertEquals(0, ((char[]) instance.encode(emptyByteArray)).length);
    }
}
