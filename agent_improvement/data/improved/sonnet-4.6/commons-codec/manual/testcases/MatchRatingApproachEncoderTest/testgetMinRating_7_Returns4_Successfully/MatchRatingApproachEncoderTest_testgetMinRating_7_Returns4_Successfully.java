package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

public class MatchRatingApproachEncoderTest_testgetMinRating_7_Returns4_Successfully extends AbstractStringEncoderTest<MatchRatingApproachEncoder> {

    @Override
    protected MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    /**
     * Verifies that a combined encoded-name length of 7 (upper boundary of the 5–7 range)
     * yields a minimum similarity rating of 4, as specified by the MRA algorithm rules.
     */
    @Test
    final void testgetMinRating_7_Returns4_Successfully() {
        // MRA specifies: sumLength in [5, 7] → minRating = 4
        final int sumLength = 7;
        final int expectedMinRating = 4;

        assertEquals(expectedMinRating, getStringEncoder().getMinRating(sumLength));
    }
}
