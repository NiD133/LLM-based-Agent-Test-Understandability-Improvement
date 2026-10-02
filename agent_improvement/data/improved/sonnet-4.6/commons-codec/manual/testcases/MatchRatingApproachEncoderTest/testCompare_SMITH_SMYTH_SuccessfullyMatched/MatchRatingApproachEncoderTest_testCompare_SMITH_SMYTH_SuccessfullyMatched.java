package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

public class MatchRatingApproachEncoderTest_testCompare_SMITH_SMYTH_SuccessfullyMatched extends AbstractStringEncoderTest<MatchRatingApproachEncoder> {

    @Override
    protected MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    /**
     * Verifies that "smith" and "smyth" are considered phonetically equivalent by the
     * Match Rating Approach (MRA) algorithm. Both names share the same consonant skeleton
     * (SM-TH) after vowel removal; "y" acts as a vowel substitute, so both encode to the
     * same MRA code and their similarity rating meets the minimum threshold.
     */
    @Test
    final void testCompare_SMITH_SMYTH_SuccessfullyMatched() {
        final String name1 = "smith";
        final String name2 = "smyth";

        // "smith" and "smyth" are phonetic variants of the same surname;
        // the MRA algorithm should recognise them as a match.
        assertTrue(getStringEncoder().isEncodeEquals(name1, name2));
    }
}
