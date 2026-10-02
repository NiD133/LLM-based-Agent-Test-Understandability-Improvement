package org.apache.commons.codec.digest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class MurmurHash2Test_testHash64String {

    private static final String TEXT_TO_HASH =
            "Lorem ipsum dolor sit amet, consectetur adipisicing elit";
    private static final long EXPECTED_HASH64 = 0x0920e0c1b7eeb261L;

    @Test
    void testHash64String() {
        final long hash = MurmurHash2.hash64(TEXT_TO_HASH);

        assertEquals(EXPECTED_HASH64, hash);
    }
}
