package org.apache.commons.codec.digest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class MurmurHash2Test_testHash32StringIntInt {

    private static final String TEXT = "Lorem ipsum dolor sit amet, consectetur adipisicing elit";
    private static final int SUBSTRING_START = 2;
    private static final int SUBSTRING_LENGTH = TEXT.length() - 4;
    private static final int EXPECTED_HASH = 0x4d666d90;

    @Test
    void testHash32StringIntInt() {
        final int hash = MurmurHash2.hash32(TEXT, SUBSTRING_START, SUBSTRING_LENGTH);

        assertEquals(EXPECTED_HASH, hash);
    }
}
