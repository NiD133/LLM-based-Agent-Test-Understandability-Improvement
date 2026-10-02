package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

public class MatchRatingApproachEncoderTest_testGetMinRating_7_Return4_Successfully extends AbstractStringEncoderTest<MatchRatingApproachEncoder> {

    @Override
    protected MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    /**
     * Verifies that a combined name-length sum of 7 (range 5–7) yields a minimum
     * similarity rating of 4, as defined by the MRA specification table.
     */
    @Test
    final void testGetMinRating_7_Return4_Successfully() {
        // sumLength = 7 falls in the range [5, 7], which maps to min rating 4
        int sumLength = 7;
        int expectedMinRating = 4;

        int actualMinRating = getStringEncoder().getMinRating(sumLength);

        assertEquals(expectedMinRating, actualMinRating);
    }
}
