package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class SoundexTest_testSimplifiedSoundex {

    private static final Soundex SIMPLIFIED_SOUNDEX = Soundex.US_ENGLISH_SIMPLIFIED;

    protected Soundex createStringEncoder() {
        return new Soundex();
    }

    @Test
    void testSimplifiedSoundex() {
        assertWestPenwithExample("W452", "WILLIAMS");
        assertWestPenwithExample("B625", "BARAGWANATH");
        assertWestPenwithExample("D540", "DONNELL");
        assertWestPenwithExample("L300", "LLOYD");
        assertWestPenwithExample("W422", "WOOLCOCK");

        assertSimplifiedSoundex("D320", "Dodds");
        assertSimplifiedSoundex("D320", "Dwdds");
        assertSimplifiedSoundex("D320", "Dhdds");
    }

    // Examples and algorithm rules from http://west-penwith.org.uk/misc/soundex.htm.
    private void assertWestPenwithExample(final String expectedSoundex, final String name) {
        assertSimplifiedSoundex(expectedSoundex, name);
    }

    private void assertSimplifiedSoundex(final String expectedSoundex, final String name) {
        assertEquals(expectedSoundex, SIMPLIFIED_SOUNDEX.encode(name));
    }
}
