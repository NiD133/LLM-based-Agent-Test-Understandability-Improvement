package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

/**
 * Tests that getMinRating returns the correct minimum similarity threshold for
 * the MRA algorithm based on combined encoded name length.
 *
 * Per the Match Rating Approach spec, a combined length in the range [8, 11]
 * requires a minimum rating of 3 for two names to be considered a match.
 */
public class MatchRatingApproachEncoderTest_testgetMinRating_11_Returns_3_Successfully extends AbstractStringEncoderTest<MatchRatingApproachEncoder> {

    @Override
    protected MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    @Test
    final void testgetMinRating_11_Returns_3_Successfully() {
        // 11 is the upper boundary of the [8, 11] range, which maps to min rating 3
        int combinedNameLength = 11;
        int expectedMinRating = 3;

        assertEquals(expectedMinRating, getStringEncoder().getMinRating(combinedNameLength));
    }
}
