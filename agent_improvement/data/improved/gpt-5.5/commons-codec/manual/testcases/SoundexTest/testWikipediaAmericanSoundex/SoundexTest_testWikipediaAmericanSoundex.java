package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class SoundexTest_testWikipediaAmericanSoundex {

    protected Soundex createStringEncoder() {
        return new Soundex();
    }

    private Soundex getStringEncoder() {
        return createStringEncoder();
    }

    /**
     * Tests examples from https://en.wikipedia.org/wiki/Soundex#American_Soundex as of 2015-03-22.
     */
    @Test
    void testWikipediaAmericanSoundex() {
        assertWikipediaSoundex("R163", "Robert");
        assertWikipediaSoundex("R163", "Rupert");
        assertWikipediaSoundex("A261", "Ashcraft");
        assertWikipediaSoundex("A261", "Ashcroft");
        assertWikipediaSoundex("T522", "Tymczak");
        assertWikipediaSoundex("P236", "Pfister");
    }

    private void assertWikipediaSoundex(final String expectedCode, final String name) {
        assertEquals(expectedCode, getStringEncoder().encode(name));
    }
}
