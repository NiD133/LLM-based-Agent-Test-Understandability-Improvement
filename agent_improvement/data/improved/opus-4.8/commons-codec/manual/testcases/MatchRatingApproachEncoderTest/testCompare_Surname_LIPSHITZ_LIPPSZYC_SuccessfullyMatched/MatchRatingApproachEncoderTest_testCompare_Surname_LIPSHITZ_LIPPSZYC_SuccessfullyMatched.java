package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link MatchRatingApproachEncoder} treats two phonetically similar
 * surnames as a match.
 */
public class MatchRatingApproachEncoderTest_testCompare_Surname_LIPSHITZ_LIPPSZYC_SuccessfullyMatched
        extends AbstractStringEncoderTest<MatchRatingApproachEncoder> {

    @Override
    protected MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    /**
     * The surnames "LIPSHITZ" and "LIPPSZYC" are different spellings of the same
     * name, so the Match Rating Approach algorithm should consider them equal.
     */
    @Test
    final void surnamesLipshitzAndLippszycAreMatched() {
        final String firstSpelling = "LIPSHITZ";
        final String secondSpelling = "LIPPSZYC";

        final boolean encodingsMatch =
                getStringEncoder().isEncodeEquals(firstSpelling, secondSpelling);

        assertTrue(encodingsMatch,
                "Phonetically equivalent surnames should be reported as matching");
    }
}
