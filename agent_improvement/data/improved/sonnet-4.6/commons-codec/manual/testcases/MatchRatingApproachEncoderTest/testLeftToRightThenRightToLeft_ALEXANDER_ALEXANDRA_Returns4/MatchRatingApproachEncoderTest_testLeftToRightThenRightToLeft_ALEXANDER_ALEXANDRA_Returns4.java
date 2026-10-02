package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

public class MatchRatingApproachEncoderTest_testLeftToRightThenRightToLeft_ALEXANDER_ALEXANDRA_Returns4 extends AbstractStringEncoderTest<MatchRatingApproachEncoder> {

    @Override
    protected MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    /**
     * Verifies that left-to-right then right-to-left character matching between
     * "ALEXANDER" and "ALEXANDRA" yields a similarity count of 4.
     *
     * The algorithm removes identical characters found at the same positions
     * (scanning both left-to-right and right-to-left), then returns 6 minus the
     * length of the longer remaining string. For these two near-identical names,
     * 4 unmatched characters remain, giving a score of 6 - 2 = 4.
     */
    @Test
    final void testLeftToRightThenRightToLeft_ALEXANDER_ALEXANDRA_Returns4() {
        final int expectedSimilarityScore = 4;
        assertEquals(expectedSimilarityScore,
                getStringEncoder().leftToRightThenRightToLeftProcessing("ALEXANDER", "ALEXANDRA"));
    }
}
