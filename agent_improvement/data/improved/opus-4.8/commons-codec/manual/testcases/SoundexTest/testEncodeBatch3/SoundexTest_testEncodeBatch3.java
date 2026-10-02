package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link Soundex} encodes a batch of surnames to the expected
 * four-character Soundex codes.
 *
 * <p>The expected codes are taken from the US National Archives Soundex examples:
 * http://www.archives.gov/research_room/genealogy/census/soundex.html</p>
 */
public class SoundexTest_testEncodeBatch3 extends AbstractStringEncoderTest<Soundex> {

    @Override
    protected Soundex createStringEncoder() {
        return new Soundex();
    }

    /**
     * Asserts that encoding {@code name} with the default US-English Soundex
     * encoder produces {@code expectedCode}.
     */
    private void assertEncodesTo(final String expectedCode, final String name) {
        assertEquals(expectedCode, getStringEncoder().encode(name));
    }

    @Test
    void testEncodeBatch3() {
        assertEncodesTo("W252", "Washington");
        assertEncodesTo("L000", "Lee");
        assertEncodesTo("G362", "Gutierrez");
        assertEncodesTo("P236", "Pfister");
        assertEncodesTo("J250", "Jackson");
        assertEncodesTo("T522", "Tymczak");
        // For VanDeusen, D-250 (D, 2 for the S, 5 for the N, 0 padding) is an
        // alternative encoding, but the default encoder produces V532.
        assertEncodesTo("V532", "VanDeusen");
    }
}
