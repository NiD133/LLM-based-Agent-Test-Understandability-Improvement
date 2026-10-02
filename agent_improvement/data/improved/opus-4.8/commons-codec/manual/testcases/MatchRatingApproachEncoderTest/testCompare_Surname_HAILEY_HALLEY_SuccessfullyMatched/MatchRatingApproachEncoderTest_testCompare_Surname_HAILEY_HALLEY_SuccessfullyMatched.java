package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link MatchRatingApproachEncoder#isEncodeEquals(String, String)}
 * recognizes two phonetically similar surnames as a match.
 *
 * <p>"Hailey" and "Halley" differ only by a single inner letter, so the Match
 * Rating Approach algorithm should consider them homophonous and report a
 * successful match.</p>
 */
public class MatchRatingApproachEncoderTest_testCompare_Surname_HAILEY_HALLEY_SuccessfullyMatched
        extends AbstractStringEncoderTest<MatchRatingApproachEncoder> {

    @Override
    protected MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    @Test
    final void testCompare_Surname_HAILEY_HALLEY_SuccessfullyMatched() {
        // Two surnames that sound alike and should be matched by the algorithm.
        final String firstSurname = "Hailey";
        final String similarSurname = "Halley";

        final boolean namesMatch = getStringEncoder().isEncodeEquals(firstSurname, similarSurname);

        assertTrue(namesMatch,
                "Expected '" + firstSurname + "' and '" + similarSurname + "' to be matched as homophonous");
    }
}
