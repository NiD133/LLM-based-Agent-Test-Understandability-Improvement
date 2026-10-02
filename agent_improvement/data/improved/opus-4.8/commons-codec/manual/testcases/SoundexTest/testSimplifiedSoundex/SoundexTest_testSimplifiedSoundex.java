package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

/**
 * Tests the "Simplified Soundex" variant exposed via {@link Soundex#US_ENGLISH_SIMPLIFIED}.
 *
 * <p>In this variant the letters H and W are <em>not</em> silent: they behave like the
 * vowels (AEIOUY). That means they are never encoded themselves, but they do act as
 * separators that break up runs of consonants sharing the same Soundex digit.</p>
 *
 * <p>Reference for the examples and algorithm rules:
 * http://west-penwith.org.uk/misc/soundex.htm</p>
 */
public class SoundexTest_testSimplifiedSoundex extends AbstractStringEncoderTest<Soundex> {

    /** The encoder under test: the Simplified Soundex variant. */
    private final Soundex simplifiedSoundex = Soundex.US_ENGLISH_SIMPLIFIED;

    @Override
    protected Soundex createStringEncoder() {
        return new Soundex();
    }

    /**
     * Asserts that the Simplified Soundex encoding of {@code input} equals {@code expectedCode}.
     */
    private void assertEncodesTo(final String expectedCode, final String input) {
        assertEquals(expectedCode, simplifiedSoundex.encode(input));
    }

    @Test
    void testSimplifiedSoundex() {
        // Canonical examples from the reference page.
        assertEncodesTo("W452", "WILLIAMS");
        assertEncodesTo("B625", "BARAGWANATH");
        assertEncodesTo("D540", "DONNELL");
        assertEncodesTo("L300", "LLOYD");
        assertEncodesTo("W422", "WOOLCOCK");

        // Baseline: two adjacent 'd' consonants collapse to a single digit.
        assertEncodesTo("D320", "Dodds");

        // Because W acts as a separator (not silent), the two 'd's around it are
        // kept distinct, yielding the same code as the vowel-separated baseline.
        assertEncodesTo("D320", "Dwdds");

        // H behaves the same way as W: it separates the surrounding 'd's.
        assertEncodesTo("D320", "Dhdds");
    }
}
