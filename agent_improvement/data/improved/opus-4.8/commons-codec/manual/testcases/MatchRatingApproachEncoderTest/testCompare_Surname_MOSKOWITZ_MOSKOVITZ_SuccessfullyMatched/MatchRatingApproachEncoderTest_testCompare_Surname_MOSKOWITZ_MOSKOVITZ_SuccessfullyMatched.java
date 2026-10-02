package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link MatchRatingApproachEncoder#isEncodeEquals(String, String)} treats two
 * phonetically similar surnames as a successful match.
 */
public class MatchRatingApproachEncoderTest_testCompare_Surname_MOSKOWITZ_MOSKOVITZ_SuccessfullyMatched
        extends AbstractStringEncoderTest<MatchRatingApproachEncoder> {

    @Override
    protected MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    /**
     * The surnames "Moskowitz" and "Moskovitz" differ only by a single consonant ('w' vs 'v') and
     * are considered homophones by the Match Rating Approach algorithm, so comparing them must
     * report a match.
     */
    @Test
    final void surnamesMoskowitzAndMoskovitzAreReportedAsMatching() {
        final String firstSurname = "Moskowitz";
        final String secondSurname = "Moskovitz";

        final boolean namesMatch = getStringEncoder().isEncodeEquals(firstSurname, secondSurname);

        assertTrue(namesMatch, "Moskowitz and Moskovitz should be recognized as a phonetic match");
    }
}
