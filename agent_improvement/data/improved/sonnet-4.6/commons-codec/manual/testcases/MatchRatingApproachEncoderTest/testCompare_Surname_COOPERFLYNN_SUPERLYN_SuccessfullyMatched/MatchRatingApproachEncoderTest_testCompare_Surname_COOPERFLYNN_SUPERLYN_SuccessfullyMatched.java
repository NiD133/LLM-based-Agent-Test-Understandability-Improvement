package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

public class MatchRatingApproachEncoderTest_testCompare_Surname_COOPERFLYNN_SUPERLYN_SuccessfullyMatched extends AbstractStringEncoderTest<MatchRatingApproachEncoder> {

    @Override
    protected MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    /**
     * Verifies that hyphenated surnames "Cooper-Flynn" and "Super-Lyn" are considered a match
     * by the Match Rating Approach algorithm. The hyphens are stripped during cleaning, so the
     * algorithm compares the phonetic codes of "COOPERFLYNN" and "SUPERLYN", which are similar
     * enough to satisfy the minimum rating threshold.
     */
    @Test
    final void testCompare_Surname_COOPERFLYNN_SUPERLYN_SuccessfullyMatched() {
        assertTrue(getStringEncoder().isEncodeEquals("Cooper-Flynn", "Super-Lyn"));
    }
}
