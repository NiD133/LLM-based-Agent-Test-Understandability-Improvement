package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link MatchRatingApproachEncoder#isEncodeEquals(String, String)}
 * treats two short, phonetically similar names as a match.
 */
public class MatchRatingApproachEncoderTest_testCompare_SmallInput_CARK_Kl_SuccessfullyMatched
        extends AbstractStringEncoderTest<MatchRatingApproachEncoder> {

    @Override
    protected MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    /**
     * "Kl" and "Karl" reduce to the same Match Rating Approach encoding, so the
     * encoder should report them as homophones (a successful match).
     */
    @Test
    final void shortSimilarNamesAreReportedAsMatching() {
        final MatchRatingApproachEncoder encoder = getStringEncoder();

        final boolean namesMatch = encoder.isEncodeEquals("Kl", "Karl");

        assertTrue(namesMatch, "'Kl' and 'Karl' should be treated as a phonetic match");
    }
}
