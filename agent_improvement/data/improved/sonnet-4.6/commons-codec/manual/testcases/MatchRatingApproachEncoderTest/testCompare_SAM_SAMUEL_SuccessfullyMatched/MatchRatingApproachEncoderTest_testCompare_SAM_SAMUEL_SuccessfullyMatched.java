package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

public class MatchRatingApproachEncoderTest_testCompare_SAM_SAMUEL_SuccessfullyMatched extends AbstractStringEncoderTest<MatchRatingApproachEncoder> {

    @Override
    protected MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    /**
     * "Sam" and "Samuel" are phonetically similar enough that the Match Rating Approach
     * considers them a match. The algorithm encodes both names and compares their similarity
     * rating against a minimum threshold derived from the combined name length.
     */
    @Test
    final void testCompare_SAM_SAMUEL_SuccessfullyMatched() {
        String shorterName = "Sam";
        String longerName = "Samuel";

        boolean arePhoneticMatches = getStringEncoder().isEncodeEquals(shorterName, longerName);

        assertTrue(arePhoneticMatches);
    }
}
