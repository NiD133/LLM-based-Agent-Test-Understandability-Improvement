package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

/**
 * Verifies {@link MatchRatingApproachEncoder#getMinRating(int)} for combined name
 * lengths in the largest bucket.
 *
 * <p>The minimum similarity rating decreases as the combined length of the two
 * compared names grows. Per the algorithm's documented thresholds, any combined
 * length of 13 or more maps to the smallest minimum rating, {@code 1}.</p>
 */
public class MatchRatingApproachEncoderTest_testGetMinRating_13_Returns_1_Successfully
        extends AbstractStringEncoderTest<MatchRatingApproachEncoder> {

    @Override
    protected MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    @Test
    final void getMinRatingForCombinedLength13ReturnsLowestRatingOf1() {
        final int combinedNameLength = 13;
        final int expectedMinRating = 1;

        final int actualMinRating = getStringEncoder().getMinRating(combinedNameLength);

        assertEquals(expectedMinRating, actualMinRating);
    }
}
