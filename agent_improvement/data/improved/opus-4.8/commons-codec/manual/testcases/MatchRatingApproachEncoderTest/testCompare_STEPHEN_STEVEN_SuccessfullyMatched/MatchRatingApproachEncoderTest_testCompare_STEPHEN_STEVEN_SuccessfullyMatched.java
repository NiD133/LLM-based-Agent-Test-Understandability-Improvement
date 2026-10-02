package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link MatchRatingApproachEncoder#isEncodeEquals(String, String)}
 * recognizes two phonetically similar names as a match.
 */
public class MatchRatingApproachEncoderTest_testCompare_STEPHEN_STEVEN_SuccessfullyMatched
        extends AbstractStringEncoderTest<MatchRatingApproachEncoder> {

    @Override
    protected MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    /**
     * "Stephen" and "Steven" sound alike, so the Match Rating Approach should
     * consider them homophonous and report them as equal.
     */
    @Test
    final void shouldMatchPhoneticallySimilarNamesStephenAndSteven() {
        final MatchRatingApproachEncoder encoder = getStringEncoder();

        final boolean namesMatch = encoder.isEncodeEquals("Stephen", "Steven");

        assertTrue(namesMatch, "Stephen and Steven should be recognized as a phonetic match");
    }
}
