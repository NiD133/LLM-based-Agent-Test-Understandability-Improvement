package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

public class MatchRatingApproachEncoderTest_testgetMinRating_8_Returns3_Successfully extends AbstractStringEncoderTest<MatchRatingApproachEncoder> {

    @Override
    protected MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    /**
     * Per the MRA algorithm spec, a combined name length in the range [8, 11]
     * requires a minimum similarity rating of 3 to qualify as a match.
     * This test exercises the lower boundary of that range (sumLength = 8).
     */
    @Test
    final void testgetMinRating_8_Returns3_Successfully() {
        final int combinedNameLength = 8;
        final int expectedMinRating = 3;

        assertEquals(expectedMinRating, getStringEncoder().getMinRating(combinedNameLength));
    }
}
