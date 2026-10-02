package org.apache.commons.codec.digest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Verifies {@link MurmurHash2#hash64(String, int, int)}, the 64-bit MurmurHash2
 * variant that hashes a substring using the default seed.
 */
public class MurmurHash2Test_testHash64StringIntInt {

    /** Sample text whose substring is hashed by the test. */
    private static final String TEXT =
            "Lorem ipsum dolor sit amet, consectetur adipisicing elit";

    /** Start index of the substring to hash. */
    private static final int FROM = 2;

    /** Length of the substring to hash (drops 2 leading and 2 trailing chars). */
    private static final int LENGTH = TEXT.length() - 4;

    /** Expected 64-bit hash for the chosen substring and default seed. */
    private static final long EXPECTED_HASH = 0xa8b33145194985a2L;

    @Test
    void testHash64StringIntInt() {
        final long actualHash = MurmurHash2.hash64(TEXT, FROM, LENGTH);

        assertEquals(EXPECTED_HASH, actualHash);
    }
}
