package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class SoundexTest_testNewInstance3 {

    protected Soundex createStringEncoder() {
        return new Soundex();
    }

    @Test
    void testNewInstance3() {
        final Soundex soundex = new Soundex(Soundex.US_ENGLISH_MAPPING_STRING);
        final String encodedName = soundex.soundex("Williams");

        assertEquals("W452", encodedName);
    }
}
