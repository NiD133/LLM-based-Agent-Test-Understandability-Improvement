package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

public class MatchRatingApproachEncoderTest_testLeftToRightThenRightToLeft_EINSTEIN_MICHAELA_Returns0 extends AbstractStringEncoderTest<MatchRatingApproachEncoder> {

    @Override
    protected MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    /**
     * leftToRightThenRightToLeftProcessing removes characters that match at the same position
     * (scanning both left-to-right and right-to-left), then returns Math.abs(6 - longerRemaining).
     * "EINSTEIN" and "MICHAELA" share no characters at matching positions, so no characters are
     * removed and the longer remainder has length 8, giving Math.abs(6 - 8) = 0 (capped at 0 by abs).
     * A similarity score of 0 means the two names are phonetically dissimilar under MRA.
     */
    @Test
    final void testLeftToRightThenRightToLeft_EINSTEIN_MICHAELA_Returns0() {
        final String name1 = "EINSTEIN";
        final String name2 = "MICHAELA";
        final int expectedSimilarityScore = 0;

        final int actualSimilarityScore = getStringEncoder().leftToRightThenRightToLeftProcessing(name1, name2);

        assertEquals(expectedSimilarityScore, actualSimilarityScore,
                "Names with no positional character matches should yield a similarity score of 0");
    }
}
