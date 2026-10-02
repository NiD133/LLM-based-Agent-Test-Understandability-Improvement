package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

/**
 * Verifies {@link MatchRatingApproachEncoder#getMinRating(int)} for the upper
 * boundary of the "rating 3" bracket.
 *
 * <p>The algorithm maps the combined length of two encoded names to a minimum
 * similarity rating: lengths from 8 to 11 (inclusive) must yield a rating of 3.
 * This test pins the upper edge of that bracket (sum length = 11).</p>
 */
public class MatchRatingApproachEncoderTest_testgetMinRating_11_Returns_3_Successfully
        extends AbstractStringEncoderTest<MatchRatingApproachEncoder> {

    @Override
    protected MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    @Test
    final void getMinRatingReturns3ForCombinedLengthOf11() {
        // 11 is the largest combined length that still maps to a minimum rating of 3.
        final int combinedNameLength = 11;
        final int expectedMinRating = 3;

        final int actualMinRating = getStringEncoder().getMinRating(combinedNameLength);

        assertEquals(expectedMinRating, actualMinRating);
    }
}
