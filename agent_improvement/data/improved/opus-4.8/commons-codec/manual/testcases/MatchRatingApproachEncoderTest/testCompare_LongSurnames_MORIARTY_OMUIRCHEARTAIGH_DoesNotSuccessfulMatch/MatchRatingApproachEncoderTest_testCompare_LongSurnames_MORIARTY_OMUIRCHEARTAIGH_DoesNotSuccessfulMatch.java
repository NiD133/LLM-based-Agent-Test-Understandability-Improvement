package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertFalse;

import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link MatchRatingApproachEncoder#isEncodeEquals(String, String)}
 * reports two long, dissimilar surnames as <em>not</em> homophonous.
 */
public class MatchRatingApproachEncoderTest_testCompare_LongSurnames_MORIARTY_OMUIRCHEARTAIGH_DoesNotSuccessfulMatch
        extends AbstractStringEncoderTest<MatchRatingApproachEncoder> {

    @Override
    protected MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    /**
     * The surnames "Moriarty" and "OMuircheartaigh" are long and phonetically
     * distinct, so the Match Rating Approach should not consider them a match.
     */
    @Test
    final void testLongDissimilarSurnamesDoNotMatch() {
        final String firstSurname = "Moriarty";
        final String secondSurname = "OMuircheartaigh";

        final boolean namesAreHomophonous =
                getStringEncoder().isEncodeEquals(firstSurname, secondSurname);

        assertFalse(namesAreHomophonous,
                "Expected '" + firstSurname + "' and '" + secondSurname + "' to be reported as a non-match");
    }
}
