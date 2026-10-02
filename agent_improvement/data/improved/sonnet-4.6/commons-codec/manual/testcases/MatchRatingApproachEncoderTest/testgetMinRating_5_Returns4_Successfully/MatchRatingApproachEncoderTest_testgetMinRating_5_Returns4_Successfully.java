package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

public class MatchRatingApproachEncoderTest_testgetMinRating_5_Returns4_Successfully extends AbstractStringEncoderTest<MatchRatingApproachEncoder> {

    @Override
    protected MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    /**
     * When the combined length of two encoded names falls in the range [5, 7],
     * the MRA algorithm specifies a minimum rating threshold of 4.
     * This verifies the lower boundary of that range (sumLength = 5).
     */
    @Test
    final void testgetMinRating_5_Returns4_Successfully() {
        // sumLength = 5 falls in the [5..7] range, which maps to minRating = 4
        int sumLength = 5;
        int expectedMinRating = 4;

        assertEquals(expectedMinRating, getStringEncoder().getMinRating(sumLength));
    }
}
