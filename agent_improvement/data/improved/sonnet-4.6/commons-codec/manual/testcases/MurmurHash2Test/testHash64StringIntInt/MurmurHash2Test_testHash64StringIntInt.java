package org.apache.commons.codec.digest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class MurmurHash2Test_testHash64StringIntInt {

    /** Sample text used as hash input. */
    static final String TEXT = "Lorem ipsum dolor sit amet, consectetur adipisicing elit";

    /** Start index of the substring to hash (skips the first 2 characters). */
    private static final int FROM = 2;

    /**
     * Number of characters to hash: the full string minus the first 2 and last 2 characters,
     * producing the substring "rem ipsum dolor sit amet, consectetur adipisicing el".
     */
    private static final int LENGTH = TEXT.length() - 4;

    /** Expected 64-bit MurmurHash2 result for the substring [FROM, FROM+LENGTH) with the default seed. */
    private static final long EXPECTED_HASH = 0xa8b33145194985a2L;

    @Test
    void testHash64StringIntInt() {
        final long hash = MurmurHash2.hash64(TEXT, FROM, LENGTH);
        assertEquals(EXPECTED_HASH, hash);
    }
}
