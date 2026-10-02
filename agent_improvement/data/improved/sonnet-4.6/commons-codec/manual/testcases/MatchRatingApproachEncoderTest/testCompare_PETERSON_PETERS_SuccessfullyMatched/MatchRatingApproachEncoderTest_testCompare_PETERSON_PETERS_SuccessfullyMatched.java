package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class MatchRatingApproachEncoderTest_testCompare_PETERSON_PETERS_SuccessfullyMatched extends AbstractStringEncoderTest<MatchRatingApproachEncoder> {

    @Override
    protected MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    /**
     * "Peterson" and "Peters" are surname variants that the MRA algorithm considers
     * phonetically equivalent: both reduce to the same consonant skeleton after
     * vowel removal, double-consonant reduction, and the 3-letter prefix/suffix
     * trimming steps, and their similarity rating meets the minimum threshold.
     */
    @Test
    @DisplayName("MRA encodes 'Peterson' and 'Peters' as phonetically equivalent names")
    final void testCompare_PETERSON_PETERS_SuccessfullyMatched() {
        final String fullSurname = "Peterson";
        final String shortSurname = "Peters";

        assertTrue(getStringEncoder().isEncodeEquals(fullSurname, shortSurname),
                "Expected 'Peterson' and 'Peters' to be considered a match by the Match Rating Approach");
    }
}
