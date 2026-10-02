package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

public class MatchRatingApproachEncoderTest_testgetMinRating_10_Returns3_Successfully extends AbstractStringEncoderTest<MatchRatingApproachEncoder> {

    @Override
    protected MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    /**
     * Per the MRA algorithm spec, the minimum rating for a combined name length in the range [8, 11] is 3.
     * A sumLength of 10 falls in this range, so getMinRating should return 3.
     */
    @Test
    final void testgetMinRating_10_Returns3_Successfully() {
        // sumLength of 10 falls in the MRA range [8..11], which maps to a min rating of 3
        int sumLength = 10;
        int expectedMinRating = 3;

        int actualMinRating = getStringEncoder().getMinRating(sumLength);

        assertEquals(expectedMinRating, actualMinRating);
    }
}
