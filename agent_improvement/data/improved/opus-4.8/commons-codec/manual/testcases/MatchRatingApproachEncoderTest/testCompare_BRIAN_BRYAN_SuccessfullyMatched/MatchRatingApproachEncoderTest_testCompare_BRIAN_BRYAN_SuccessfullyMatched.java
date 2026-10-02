package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link MatchRatingApproachEncoder#isEncodeEquals(String, String)} treats the
 * phonetically similar names "Brian" and "Bryan" as a match.
 */
public class MatchRatingApproachEncoderTest_testCompare_BRIAN_BRYAN_SuccessfullyMatched
        extends AbstractStringEncoderTest<MatchRatingApproachEncoder> {

    @Override
    protected MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    @Test
    final void brianAndBryanAreConsideredHomophones() {
        final MatchRatingApproachEncoder encoder = getStringEncoder();

        final boolean namesMatch = encoder.isEncodeEquals("Brian", "Bryan");

        assertTrue(namesMatch, "'Brian' and 'Bryan' should be recognized as a phonetic match");
    }
}
