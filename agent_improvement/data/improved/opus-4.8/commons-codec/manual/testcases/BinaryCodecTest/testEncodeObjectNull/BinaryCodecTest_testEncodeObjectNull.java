package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests {@link BinaryCodec#encode(Object)} for the empty-input case.
 */
public class BinaryCodecTest_testEncodeObjectNull {

    /** The codec under test, recreated before each test. */
    private BinaryCodec codec;

    @BeforeEach
    void setUp() {
        codec = new BinaryCodec();
    }

    /**
     * Encoding an empty byte array via {@code encode(Object)} should yield an
     * empty char array, since there are no bits to expand into ASCII '0'/'1'.
     */
    @Test
    void testEncodeObjectNull() throws Exception {
        final Object emptyInput = new byte[0];

        final char[] encoded = (char[]) codec.encode(emptyInput);

        assertEquals(0, encoded.length);
    }
}
