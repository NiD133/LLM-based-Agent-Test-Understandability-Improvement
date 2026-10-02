package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class SoundexTest_testNewInstance2 {

    @Test
    void testNewInstance2() {
        final Soundex soundex = new Soundex(Soundex.US_ENGLISH_MAPPING_STRING.toCharArray());
        final String encodedName = soundex.soundex("Williams");

        assertEquals("W452", encodedName);
    }
}
