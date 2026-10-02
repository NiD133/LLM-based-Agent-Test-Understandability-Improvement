package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link MatchRatingApproachEncoder#isEncodeEquals(String, String)}
 * recognizes two phonetically similar names as a match.
 */
public class MatchRatingApproachEncoderTest_testCompare_FRANCISZEK_FRANCES_SuccessfullyMatched
        extends AbstractStringEncoderTest<MatchRatingApproachEncoder> {

    @Override
    protected MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    /**
     * "Franciszek" and "Frances" share enough Match Rating Approach encoding,
     * so they should be reported as a homophonous (matching) pair.
     */
    @Test
    final void encodingsOfFranciszekAndFrancesAreEqual() {
        final MatchRatingApproachEncoder encoder = getStringEncoder();

        final boolean namesMatch = encoder.isEncodeEquals("Franciszek", "Frances");

        assertTrue(namesMatch, "Franciszek and Frances should be matched by the Match Rating Approach");
    }
}
