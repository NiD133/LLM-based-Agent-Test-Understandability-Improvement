package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

public class MatchRatingApproachEncoderTest_testCompare_STEPHEN_STEFAN_SuccessfullyMatched extends AbstractStringEncoderTest<MatchRatingApproachEncoder> {

    @Override
    protected MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    /**
     * Verifies that "Stephen" and "Stefan" are treated as phonetic matches by the
     * Match Rating Approach algorithm. Both names share the same consonant skeleton
     * (STF/STN) after vowel removal and double-consonant reduction, so their MRA
     * similarity rating should meet the minimum threshold required for a match.
     */
    @Test
    final void testCompare_STEPHEN_STEFAN_SuccessfullyMatched() {
        assertTrue(
            getStringEncoder().isEncodeEquals("Stephen", "Stefan"),
            "\"Stephen\" and \"Stefan\" should be phonetically equivalent under the Match Rating Approach"
        );
    }
}
