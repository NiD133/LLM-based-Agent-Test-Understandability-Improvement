package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link MatchRatingApproachEncoder} recognizes two phonetically
 * similar surnames as a match.
 */
public class MatchRatingApproachEncoderTest_testCompare_Surname_AUERBACH_UHRBACH_SuccessfullyMatched
        extends AbstractStringEncoderTest<MatchRatingApproachEncoder> {

    @Override
    protected MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    /**
     * "Auerbach" and "Uhrbach" are homophones, so the Match Rating Approach
     * algorithm should report them as equal.
     */
    @Test
    final void testCompare_Surname_AUERBACH_UHRBACH_SuccessfullyMatched() {
        final String firstSurname = "Auerbach";
        final String secondSurname = "Uhrbach";

        final boolean namesMatch = getStringEncoder().isEncodeEquals(firstSurname, secondSurname);

        assertTrue(namesMatch, "Auerbach and Uhrbach should be matched as phonetically equal");
    }
}
