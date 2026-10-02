package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

/**
 * Tests that {@link MatchRatingApproachEncoder#isEncodeEquals(String, String)} recognizes two
 * phonetically similar names as a match.
 */
public class MatchRatingApproachEncoderTest_testCompare_SOPHIE_SOFIA_SuccessfullyMatched
        extends AbstractStringEncoderTest<MatchRatingApproachEncoder> {

    @Override
    protected MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    /**
     * The names "Sophie" and "Sofia" are spelled differently but sound alike, so the Match Rating
     * Approach algorithm should consider their encodings equal.
     */
    @Test
    final void isEncodeEquals_returnsTrue_forPhoneticallyMatchingNames() {
        final String firstName = "Sophie";
        final String phoneticallySimilarName = "Sofia";

        final boolean namesMatch =
                getStringEncoder().isEncodeEquals(firstName, phoneticallySimilarName);

        assertTrue(namesMatch, "\"Sophie\" and \"Sofia\" should be recognized as a phonetic match");
    }
}
