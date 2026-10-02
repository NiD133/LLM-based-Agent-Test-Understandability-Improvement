package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class SoundexTest_testNewInstance {

    protected Soundex createStringEncoder() {
        return new Soundex();
    }

    /**
     * Verifies the default Soundex instance behavior covered by CODEC-54 and CODEC-56.
     */
    @Test
    void testNewInstance() {
        final Soundex defaultSoundex = new Soundex();

        assertEquals("W452", defaultSoundex.soundex("Williams"));
    }
}
