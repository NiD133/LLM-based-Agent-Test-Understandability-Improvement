package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

/**
 * Verifies {@link MatchRatingApproachEncoder#getMinRating(int)}.
 * <p>
 * {@code getMinRating} maps the combined length of two encoded names to a
 * minimum similarity rating: the longer the combined length, the lower the
 * required rating. A combined length in the range 8..11 maps to a rating of 3.
 * </p>
 */
public class MatchRatingApproachEncoderTest_testgetMinRating_8_Returns3_Successfully
        extends AbstractStringEncoderTest<MatchRatingApproachEncoder> {

    @Override
    protected MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    @Test
    final void testgetMinRating_8_Returns3_Successfully() {
        // 8 is the lower bound of the "8..11 -> rating 3" range.
        final int combinedNameLength = 8;
        final int expectedMinRating = 3;

        final int actualMinRating = getStringEncoder().getMinRating(combinedNameLength);

        assertEquals(expectedMinRating, actualMinRating);
    }
}
