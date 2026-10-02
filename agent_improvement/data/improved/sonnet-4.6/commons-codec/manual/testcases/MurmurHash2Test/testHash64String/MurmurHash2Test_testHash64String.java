package org.apache.commons.codec.digest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class MurmurHash2Test_testHash64String {

    // A well-known ASCII string used to produce a stable, pre-computed hash value.
    private static final String LOREM_IPSUM =
            "Lorem ipsum dolor sit amet, consectetur adipisicing elit";

    // Expected 64-bit MurmurHash2 of LOREM_IPSUM using the default seed (0xe17a1465),
    // pre-computed from the reference implementation.
    private static final long EXPECTED_HASH64 = 0x0920e0c1b7eeb261L;

    @Test
    void testHash64String() {
        final long actualHash = MurmurHash2.hash64(LOREM_IPSUM);
        assertEquals(EXPECTED_HASH64, actualHash,
                "hash64(String) should produce the correct 64-bit MurmurHash2 for a known input");
    }
}
