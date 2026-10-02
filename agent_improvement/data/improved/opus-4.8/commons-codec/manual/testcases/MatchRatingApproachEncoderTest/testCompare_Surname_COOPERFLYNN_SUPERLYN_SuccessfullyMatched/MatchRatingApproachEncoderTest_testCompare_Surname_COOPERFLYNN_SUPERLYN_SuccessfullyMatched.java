package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link MatchRatingApproachEncoder#isEncodeEquals(String, String)} treats two
 * phonetically similar surnames as a match.
 *
 * <p>The Match Rating Approach (MRA) algorithm cleans, de-vowels and trims each name down to its
 * first/last three characters, then counts how many characters remain after pairwise cancellation.
 * The surnames "Cooper-Flynn" and "Super-Lyn" reduce to similar codes whose similarity rating meets
 * the minimum required for a match, so they are considered homophonous.</p>
 */
class MatchRatingApproachEncoderTest_testCompare_Surname_COOPERFLYNN_SUPERLYN_SuccessfullyMatched
        extends AbstractStringEncoderTest<MatchRatingApproachEncoder> {

    @Override
    protected MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    @Test
    final void testCompare_Surname_COOPERFLYNN_SUPERLYN_SuccessfullyMatched() {
        // Two phonetically similar surnames are expected to match under the MRA algorithm.
        final String firstSurname = "Cooper-Flynn";
        final String secondSurname = "Super-Lyn";

        final boolean surnamesMatch = getStringEncoder().isEncodeEquals(firstSurname, secondSurname);

        assertTrue(surnamesMatch,
                "'" + firstSurname + "' and '" + secondSurname + "' should be recognised as a phonetic match");
    }
}
