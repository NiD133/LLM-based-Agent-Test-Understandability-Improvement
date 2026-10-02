package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

/**
 * Tests {@link MatchRatingApproachEncoder#getMinRating(int)} for the case where
 * the combined name-length sum is at most 4, which maps to the highest minimum
 * similarity rating of 5 according to the MRA specification.
 */
public class MatchRatingApproachEncoderTest_testGetMinRating_1_Returns5_Successfully extends AbstractStringEncoderTest<MatchRatingApproachEncoder> {

    /** Combined length of two encoded names used as input (falls in the &le;4 bucket). */
    private static final int SUM_LENGTH_IN_SHORTEST_BUCKET = 1;

    /** Expected minimum rating when the combined length is &le;4. */
    private static final int EXPECTED_MIN_RATING = 5;

    @Override
    protected MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    @Test
    final void testGetMinRating_1_Returns5_Successfully() {
        // A combined encoded-name length of 1 is within the <=4 range,
        // so the MRA algorithm requires at least 5 matching characters.
        assertEquals(EXPECTED_MIN_RATING, getStringEncoder().getMinRating(SUM_LENGTH_IN_SHORTEST_BUCKET));
    }
}
