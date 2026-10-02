package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

/**
 * Verifies {@link MatchRatingApproachEncoder#getMinRating(int)} at the upper
 * boundary of the "rating 4" bucket.
 *
 * <p>{@code getMinRating} maps the combined length of two encoded names to a
 * minimum similarity rating. A combined length of 5, 6 or 7 falls into the
 * {@code (4 < sumLength <= 7)} band, which yields a minimum rating of 4.
 * This test pins the inclusive upper edge of that band, {@code sumLength == 7}.</p>
 */
public class MatchRatingApproachEncoderTest_testgetMinRating_7_Returns4_Successfully
        extends AbstractStringEncoderTest<MatchRatingApproachEncoder> {

    @Override
    protected MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    @Test
    final void testGetMinRatingForSumLengthSevenReturnsFour() {
        final int sumLength = 7;
        final int expectedMinRating = 4;

        final int actualMinRating = getStringEncoder().getMinRating(sumLength);

        assertEquals(expectedMinRating, actualMinRating);
    }
}
