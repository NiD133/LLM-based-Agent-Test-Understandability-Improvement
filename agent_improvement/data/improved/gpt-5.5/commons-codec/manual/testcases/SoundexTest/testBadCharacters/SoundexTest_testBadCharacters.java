package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class SoundexTest_testBadCharacters {

    protected Soundex createStringEncoder() {
        return new Soundex();
    }

    private Soundex getStringEncoder() {
        return createStringEncoder();
    }

    @Test
    void testBadCharacters() {
        assertEquals("H452", getStringEncoder().encode("HOL>MES"));
    }
}
