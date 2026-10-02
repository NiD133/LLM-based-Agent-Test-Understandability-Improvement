package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link Soundex#US_ENGLISH_SIMPLIFIED}, which implements the Simplified Soundex algorithm.
 *
 * <p>The key difference from the standard {@link Soundex#US_ENGLISH} variant is how 'H' and 'W'
 * are treated: in Simplified Soundex they act as <em>separators</em> (behaving like the vowels
 * A, E, I, O, U, Y), whereas in standard Soundex they are completely silent (ignored entirely).
 * A separator resets the "last digit" tracker, so two identical consonant codes on either side
 * of an H or W are <strong>not</strong> merged — each produces its own digit.</p>
 *
 * @see <a href="http://west-penwith.org.uk/misc/soundex.htm">Simplified Soundex reference</a>
 */
public class SoundexTest_testSimplifiedSoundex extends AbstractStringEncoderTest<Soundex> {

    @Override
    protected Soundex createStringEncoder() {
        return new Soundex();
    }

    /**
     * Verifies Simplified Soundex codes for the canonical reference examples and for
     * inputs that isolate the separator role of 'W' and 'H'.
     */
    @Test
    void testSimplifiedSoundex() {
        final Soundex simplifiedEncoder = Soundex.US_ENGLISH_SIMPLIFIED;

        // Canonical examples taken directly from the Simplified Soundex algorithm reference
        assertEquals("W452", simplifiedEncoder.encode("WILLIAMS"));
        assertEquals("B625", simplifiedEncoder.encode("BARAGWANATH"));
        assertEquals("D540", simplifiedEncoder.encode("DONNELL"));
        assertEquals("L300", simplifiedEncoder.encode("LLOYD"));
        assertEquals("W422", simplifiedEncoder.encode("WOOLCOCK"));

        // Baseline: "Dodds" → D (first letter) + o (separator) + d→2 + d (duplicate, skipped) + s→3 = D320
        assertEquals("D320", simplifiedEncoder.encode("Dodds"));

        // 'W' as separator: "Dwdds" → D + w (separator, resets last-digit) + d→2 + d (dup, skip) + s→3 = D320
        assertEquals("D320", simplifiedEncoder.encode("Dwdds"));

        // 'H' as separator: "Dhdds" → D + h (separator, resets last-digit) + d→2 + d (dup, skip) + s→3 = D320
        assertEquals("D320", simplifiedEncoder.encode("Dhdds"));
    }
}
