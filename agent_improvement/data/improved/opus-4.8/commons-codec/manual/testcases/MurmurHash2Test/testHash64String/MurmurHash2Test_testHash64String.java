package org.apache.commons.codec.digest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Verifies {@link MurmurHash2#hash64(String)}, the 64-bit MurmurHash2 variant
 * that hashes a string using the library's default seed.
 */
public class MurmurHash2Test_testHash64String {

    /** Sample text whose 64-bit hash is checked against a known-good value. */
    private static final String INPUT_TEXT =
            "Lorem ipsum dolor sit amet, consectetur adipisicing elit";

    /** Expected 64-bit hash of {@link #INPUT_TEXT} using the default seed. */
    private static final long EXPECTED_HASH_64 = 0x0920e0c1b7eeb261L;

    @Test
    void testHash64String() {
        final long actualHash = MurmurHash2.hash64(INPUT_TEXT);

        assertEquals(EXPECTED_HASH_64, actualHash);
    }
}
