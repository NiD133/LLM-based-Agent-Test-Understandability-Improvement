package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertFalse;

import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

/**
 * Verifies how {@link MatchRatingApproachEncoder#isEncodeEquals(String, String)}
 * handles the Irish forename "Úna" and its anglicised spelling "Oonagh".
 */
public class MatchRatingApproachEncoderTest_testCompare_Forenames_UNA_OONAGH_ShouldSuccessfullyMatchButDoesNot extends AbstractStringEncoderTest<MatchRatingApproachEncoder> {

    @Override
    protected MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    /**
     * "Úna" and "Oonagh" are the same name in real life, so a perfect phonetic
     * algorithm would treat them as a match. The Match Rating Approach, however,
     * does not recognise them as homophones, so {@code isEncodeEquals} reports
     * {@code false}. This (disappointing) behaviour is the documented outcome we lock in.
     */
    @Test
    final void isEncodeEquals_unaVsOonagh_doesNotMatchDespiteBeingTheSameName() {
        final MatchRatingApproachEncoder encoder = getStringEncoder();

        final boolean namesAreConsideredEqual = encoder.isEncodeEquals("Úna", "Oonagh");

        assertFalse(namesAreConsideredEqual,
                "Match Rating Approach unfortunately fails to match \"Úna\" with \"Oonagh\"");
    }
}
