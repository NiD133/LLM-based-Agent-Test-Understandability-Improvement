package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

/**
 * Verifies that the Match Rating Approach (MRA) encoder considers "Hailey" and "Halley" to be
 * phonetically equivalent. Both names share the same consonant skeleton (HLY) after the MRA
 * preprocessing steps (vowel removal, double-consonant reduction, and truncation to first/last 3
 * letters), so their similarity rating meets the minimum threshold required for a match.
 */
public class MatchRatingApproachEncoderTest_testCompare_Surname_HAILEY_HALLEY_SuccessfullyMatched
        extends AbstractStringEncoderTest<MatchRatingApproachEncoder> {

    @Override
    protected MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    @Test
    final void testCompare_Surname_HAILEY_HALLEY_SuccessfullyMatched() {
        // "Hailey" and "Halley" differ only by the vowel sequence between H and L,
        // which the MRA algorithm discards, so they should be recognised as a match.
        assertTrue(getStringEncoder().isEncodeEquals("Hailey", "Halley"));
    }
}
