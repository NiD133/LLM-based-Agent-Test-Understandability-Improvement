package org.apache.commons.codec.digest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class MurmurHash2Test_testHash64StringIntInt {

    private static final String TEXT = "Lorem ipsum dolor sit amet, consectetur adipisicing elit";
    private static final int START_INDEX = 2;
    private static final int OMITTED_TRAILING_CHARACTERS = 4;
    private static final long EXPECTED_HASH = 0xa8b33145194985a2L;

    @Test
    void testHash64StringIntInt() {
        final long hash = MurmurHash2.hash64(TEXT, START_INDEX, TEXT.length() - OMITTED_TRAILING_CHARACTERS);

        assertEquals(EXPECTED_HASH, hash);
    }
}
