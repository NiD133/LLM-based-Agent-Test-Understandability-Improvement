package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

/**
 * Tests that {@link MatchRatingApproachEncoder#isEncodeEquals(String, String)} treats the
 * phonetically similar names "Sam" and "Samuel" as a successful match.
 */
public class MatchRatingApproachEncoderTest_testCompare_SAM_SAMUEL_SuccessfullyMatched
        extends AbstractStringEncoderTest<MatchRatingApproachEncoder> {

    @Override
    protected MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    @Test
    final void isEncodeEquals_matchesSamWithSamuel() {
        final String name = "Sam";
        final String phoneticallySimilarName = "Samuel";

        final boolean namesMatch =
                getStringEncoder().isEncodeEquals(name, phoneticallySimilarName);

        assertTrue(namesMatch,
                "'Sam' and 'Samuel' should be considered a match by the Match Rating Approach");
    }
}
