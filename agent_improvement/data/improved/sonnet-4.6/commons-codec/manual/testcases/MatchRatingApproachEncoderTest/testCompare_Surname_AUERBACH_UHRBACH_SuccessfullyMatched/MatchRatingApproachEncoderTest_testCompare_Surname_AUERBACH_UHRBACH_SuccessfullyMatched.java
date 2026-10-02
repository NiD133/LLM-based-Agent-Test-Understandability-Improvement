package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

public class MatchRatingApproachEncoderTest_testCompare_Surname_AUERBACH_UHRBACH_SuccessfullyMatched extends AbstractStringEncoderTest<MatchRatingApproachEncoder> {

    @Override
    protected MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    /**
     * "Auerbach" and "Uhrbach" are phonetically similar surnames that the MRA algorithm
     * should recognise as a match: both reduce to the same consonant skeleton after vowel
     * removal and double-consonant collapsing, and their similarity rating meets the
     * minimum threshold required for a positive match.
     */
    @Test
    final void testCompare_Surname_AUERBACH_UHRBACH_SuccessfullyMatched() {
        MatchRatingApproachEncoder encoder = getStringEncoder();
        assertTrue(encoder.isEncodeEquals("Auerbach", "Uhrbach"));
    }
}
