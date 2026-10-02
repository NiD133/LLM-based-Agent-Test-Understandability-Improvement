package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.commons.codec.AbstractStringEncoderTest;
import org.apache.commons.codec.EncoderException;
import org.junit.jupiter.api.Test;

/**
 * Tests {@link Soundex#difference(String, String)}, which encodes both inputs
 * and reports how many of the four Soundex characters match. The score ranges
 * from 0 (little or no similarity) to 4 (strong similarity or identical codes).
 */
public class SoundexTest_testDifference extends AbstractStringEncoderTest<Soundex> {

    @Override
    protected Soundex createStringEncoder() {
        return new Soundex();
    }

    /**
     * Asserts that comparing {@code left} and {@code right} yields the expected
     * Soundex difference score.
     */
    private void assertDifference(final int expectedScore, final String left, final String right)
            throws EncoderException {
        assertEquals(expectedScore, getStringEncoder().difference(left, right));
    }

    @Test
    void testDifference() throws EncoderException {
        // Edge cases: null and blank inputs encode to nothing, so they score 0.
        assertDifference(0, null, null);
        assertDifference(0, "", "");
        assertDifference(0, " ", " ");

        // Normal cases ranging from a perfect match down to no similarity.
        assertDifference(4, "Smith", "Smythe");
        assertDifference(2, "Ann", "Andrew");
        assertDifference(1, "Margaret", "Andrew");
        assertDifference(0, "Janet", "Margaret");

        // Examples from the MS T-SQL DIFFERENCE documentation (ts_de-dz_8co5).
        assertDifference(4, "Green", "Greene");
        assertDifference(0, "Blotchet-Halls", "Greene");

        // Examples from the MS T-SQL DIFFERENCE documentation (ts_setu-sus_3o6w).
        assertDifference(4, "Smith", "Smythe");
        assertDifference(4, "Smithers", "Smythers");
        assertDifference(2, "Anothers", "Brothers");
    }
}
