package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.commons.codec.AbstractStringEncoderTest;
import org.apache.commons.codec.EncoderException;
import org.junit.jupiter.api.Test;

/**
 * Tests {@link RefinedSoundex#difference(String, String)}, which reports how many
 * leading characters two Refined Soundex codes share. A higher number means the
 * two inputs sound more alike (0 = no similarity).
 */
public class RefinedSoundexTest_testDifference extends AbstractStringEncoderTest<RefinedSoundex> {

    @Override
    protected RefinedSoundex createStringEncoder() {
        return new RefinedSoundex();
    }

    /**
     * Asserts that comparing {@code left} and {@code right} yields the expected
     * similarity score.
     */
    private void assertDifference(final int expectedScore, final String left, final String right)
            throws EncoderException {
        assertEquals(expectedScore, getStringEncoder().difference(left, right));
    }

    @Test
    void testDifference() throws EncoderException {
        // Edge cases: empty or blank inputs have no shared code characters.
        assertDifference(0, null, null);
        assertDifference(0, "", "");
        assertDifference(0, " ", " ");

        // Typical name comparisons.
        assertDifference(6, "Smith", "Smythe");
        assertDifference(3, "Ann", "Andrew");
        assertDifference(1, "Margaret", "Andrew");
        assertDifference(1, "Janet", "Margaret");

        // Examples from MS T-SQL DIFFERENCE documentation (ts_de-dz_8co5).
        assertDifference(5, "Green", "Greene");
        assertDifference(1, "Blotchet-Halls", "Greene");

        // Examples from MS T-SQL DIFFERENCE documentation (ts_setu-sus_3o6w).
        assertDifference(6, "Smith", "Smythe");
        assertDifference(8, "Smithers", "Smythers");
        assertDifference(5, "Anothers", "Brothers");
    }
}
