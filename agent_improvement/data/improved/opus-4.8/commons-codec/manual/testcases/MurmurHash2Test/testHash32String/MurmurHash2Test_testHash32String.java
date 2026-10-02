package org.apache.commons.codec.digest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Verifies the 32-bit string overload {@link MurmurHash2#hash32(String)}.
 */
public class MurmurHash2Test_testHash32String {

    /** Sample text whose 32-bit MurmurHash2 value is known and fixed. */
    private static final String INPUT_TEXT =
            "Lorem ipsum dolor sit amet, consectetur adipisicing elit";

    /** Expected 32-bit hash of {@link #INPUT_TEXT} using the default seed. */
    private static final int EXPECTED_HASH_32 = 0xb3bf597e;

    @Test
    void testHash32String() {
        final int actualHash = MurmurHash2.hash32(INPUT_TEXT);

        assertEquals(EXPECTED_HASH_32, actualHash);
    }
}
