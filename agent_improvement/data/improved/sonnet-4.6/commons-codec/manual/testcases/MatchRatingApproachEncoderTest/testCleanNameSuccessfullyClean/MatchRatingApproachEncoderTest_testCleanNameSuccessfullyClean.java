package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

public class MatchRatingApproachEncoderTest_testCleanNameSuccessfullyClean extends AbstractStringEncoderTest<MatchRatingApproachEncoder> {

    @Override
    protected MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    /**
     * Verifies that cleanName applies all four normalization steps in sequence:
     *   1. Upper-case  ("This-ís   a t.,es &t" -> "THIS-ÍS   A T.,ES &T")
     *   2. Strip punctuation (hyphens, ampersands, apostrophes, periods, commas)
     *                         -> "THISÍS   A TES T"
     *   3. Remove accents     (Í -> I)  -> "THISIS   A TES T"
     *   4. Collapse/remove spaces       -> "THISISATEST"
     */
    @Test
    final void testCleanNameSuccessfullyClean() {
        // Input deliberately contains mixed case, accented characters,
        // extra spaces, and punctuation that cleanName must eliminate.
        final String messyName = "This-ís   a t.,es &t";

        // After upper-casing, stripping punctuation, removing accents,
        // and collapsing all whitespace the result is a plain ASCII string.
        final String expectedCleanName = "THISISATEST";

        assertEquals(expectedCleanName, getStringEncoder().cleanName(messyName));
    }
}
