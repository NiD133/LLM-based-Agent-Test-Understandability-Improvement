package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertFalse;

import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link MatchRatingApproachEncoder#isEncodeEquals(String, String)}
 * reports two phonetically dissimilar forenames as <em>not</em> matching.
 */
public class MatchRatingApproachEncoderTest_testCompare_Forenames_SEAN_PETE_NoMatchExpected
        extends AbstractStringEncoderTest<MatchRatingApproachEncoder> {

    @Override
    protected MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    /**
     * The forenames "Sean" and "Pete" are not homophones, so the Match Rating
     * Approach algorithm should consider them a non-match.
     */
    @Test
    final void testCompare_Forenames_SEAN_PETE_NoMatchExpected() {
        final String firstForename = "Sean";
        final String secondForename = "Pete";

        final boolean namesMatch =
                getStringEncoder().isEncodeEquals(firstForename, secondForename);

        assertFalse(namesMatch, "'Sean' and 'Pete' should not be encoded as a match");
    }
}
