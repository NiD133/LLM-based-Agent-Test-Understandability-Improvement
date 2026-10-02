package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

public class MatchRatingApproachEncoderTest_testgetMinRating_6_Returns4_Successfully extends AbstractStringEncoderTest<MatchRatingApproachEncoder> {

    @Override
    protected MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    /**
     * A combined encoded-name length of 6 falls in the 5–7 range, so the MRA
     * algorithm assigns a minimum rating threshold of 4.
     */
    @Test
    final void testgetMinRating_6_Returns4_Successfully() {
        // sumLength=6 is in the range [5,7], which maps to minRating=4
        final int sumLength = 6;
        final int expectedMinRating = 4;

        assertEquals(expectedMinRating, getStringEncoder().getMinRating(sumLength));
    }
}
