package org.apache.commons.codec.digest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class MurmurHash2Test_testHash32String {

    /**
     * Sample text used as input for hash32(String) tests.
     */
    static final String text = "Lorem ipsum dolor sit amet, consectetur adipisicing elit";

    /**
     * Expected MurmurHash2 32-bit hash of {@link #text} using the default seed (0x9747b28c),
     * pre-computed from the reference implementation.
     */
    static final int EXPECTED_HASH32_OF_TEXT = 0xb3bf597e;

    @Test
    void testHash32String() {
        final int hash = MurmurHash2.hash32(text);
        assertEquals(EXPECTED_HASH32_OF_TEXT, hash);
    }
}
