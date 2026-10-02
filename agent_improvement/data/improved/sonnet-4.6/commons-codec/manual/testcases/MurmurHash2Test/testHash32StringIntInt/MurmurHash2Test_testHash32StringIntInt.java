package org.apache.commons.codec.digest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class MurmurHash2Test_testHash32StringIntInt {

    private static final String LOREM_IPSUM = "Lorem ipsum dolor sit amet, consectetur adipisicing elit";

    // Expected 32-bit MurmurHash2 of LOREM_IPSUM.substring(2, length-4) with default seed
    private static final int EXPECTED_HASH = 0x4d666d90;

    @Test
    void testHash32StringIntInt() {
        int startIndex = 2;
        int substringLength = LOREM_IPSUM.length() - 4;

        final int hash = MurmurHash2.hash32(LOREM_IPSUM, startIndex, substringLength);

        assertEquals(EXPECTED_HASH, hash);
    }
}
