package org.apache.commons.codec.digest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class MurmurHash2Test_testHash32String {

    private static final String TEXT_TO_HASH = "Lorem ipsum dolor sit amet, consectetur adipisicing elit";
    private static final int EXPECTED_HASH32 = 0xb3bf597e;

    @Test
    void testHash32String() {
        final int actualHash = MurmurHash2.hash32(TEXT_TO_HASH);

        assertEquals(EXPECTED_HASH32, actualHash);
    }
}
