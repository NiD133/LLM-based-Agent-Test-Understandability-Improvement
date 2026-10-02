package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class SoundexTest_testHWRuleEx2 {

    private static final String BOOTH_DAVIS_SOUNDEX = "B312";
    private final Soundex stringEncoder = createStringEncoder();

    protected Soundex createStringEncoder() {
        return new Soundex();
    }

    private Soundex getStringEncoder() {
        return stringEncoder;
    }

    /**
     * Consonants from the same code group separated by W or H are treated as one.
     *
     * Test data from http://www.myatt.demon.co.uk/sxalg.htm
     */
    @Test
    void testHWRuleEx2() {
        assertEquals(BOOTH_DAVIS_SOUNDEX, getStringEncoder().encode("BOOTHDAVIS"));
        assertEquals(BOOTH_DAVIS_SOUNDEX, getStringEncoder().encode("BOOTH-DAVIS"));
    }
}
