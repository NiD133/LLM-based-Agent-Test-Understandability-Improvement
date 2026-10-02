package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link MatchRatingApproachEncoder#isEncodeEquals(String, String)} treats two
 * phonetically similar surnames as a match.
 */
public class MatchRatingApproachEncoderTest_testCompare_Surname_LEWINSKY_LEVINSKI_SuccessfullyMatched
        extends AbstractStringEncoderTest<MatchRatingApproachEncoder> {

    @Override
    protected MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    /**
     * The surnames "LEWINSKY" and "LEVINSKI" sound alike, so the Match Rating Approach
     * should consider them homophones and report them as equal.
     */
    @Test
    final void surnamesThatSoundAlikeAreReportedAsMatching() {
        final String firstSurname = "LEWINSKY";
        final String similarSurname = "LEVINSKI";

        final boolean encodingsMatch =
                getStringEncoder().isEncodeEquals(firstSurname, similarSurname);

        assertTrue(encodingsMatch,
                "Phonetically similar surnames should be reported as matching");
    }
}
