package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

/**
 * Tests that {@link MatchRatingApproachEncoder#isEncodeEquals(String, String)}
 * recognizes two phonetically similar surnames as a match.
 */
public class MatchRatingApproachEncoderTest_testCompare_BURNS_BOURNE_SuccessfullyMatched
        extends AbstractStringEncoderTest<MatchRatingApproachEncoder> {

    @Override
    protected MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    /**
     * "Burns" and "Bourne" are homophones, so the Match Rating Approach
     * algorithm should consider them equal.
     */
    @Test
    final void testCompare_BURNS_BOURNE_SuccessfullyMatched() {
        final String firstName = "Burns";
        final String similarSoundingName = "Bourne";

        final boolean namesMatch =
                getStringEncoder().isEncodeEquals(firstName, similarSoundingName);

        assertTrue(namesMatch,
                "Expected '" + firstName + "' and '" + similarSoundingName
                        + "' to be treated as a phonetic match");
    }
}
