package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertFalse;
import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

public class MatchRatingApproachEncoderTest_testCompare_Forenames_UNA_OONAGH_ShouldSuccessfullyMatchButDoesNot extends AbstractStringEncoderTest<MatchRatingApproachEncoder> {

    @Override
    protected MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    /**
     * Documents a known limitation of the MRA algorithm: the Irish forenames "Úna" (accented)
     * and "Oonagh" are phonetically equivalent variants of the same name, but the algorithm
     * fails to recognise them as a match. The accent on "Ú" causes the cleaned encoding of
     * "Úna" to differ enough from "Oonagh" that isEncodeEquals returns false instead of true.
     */
    @Test
    final void testCompare_Forenames_UNA_OONAGH_ShouldSuccessfullyMatchButDoesNot() {
        boolean encodingsMatch = getStringEncoder().isEncodeEquals("Úna", "Oonagh");
        // Known limitation: MRA does not equate these phonetically equivalent Irish names
        assertFalse(encodingsMatch);
    }
}
