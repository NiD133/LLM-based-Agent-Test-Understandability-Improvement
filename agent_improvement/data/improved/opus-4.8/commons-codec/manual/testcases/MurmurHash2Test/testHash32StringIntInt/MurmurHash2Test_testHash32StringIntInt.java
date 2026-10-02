package org.apache.commons.codec.digest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Verifies {@link MurmurHash2#hash32(String, int, int)}, the 32-bit hash of a
 * substring with the default seed.
 */
public class MurmurHash2Test_testHash32StringIntInt {

    /** Sample text whose substring is hashed. */
    private static final String TEXT = "Lorem ipsum dolor sit amet, consectetur adipisicing elit";

    @Test
    void testHash32StringIntInt() {
        // Hash the substring that starts at index 2 and drops the last 2 characters
        // as well (length = total - 4), then compare against the known result.
        final int from = 2;
        final int length = TEXT.length() - 4;

        final int actualHash = MurmurHash2.hash32(TEXT, from, length);

        final int expectedHash = 0x4d666d90;
        assertEquals(expectedHash, actualHash);
    }
}
