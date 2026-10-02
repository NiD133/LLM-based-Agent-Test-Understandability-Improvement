package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

/**
 * Verifies {@link MatchRatingApproachEncoder#getMinRating(int)}.
 * <p>
 * {@code getMinRating} maps the combined length of two encoded names to a
 * minimum similarity rating: the longer the combined length, the lower the
 * required rating. A combined length in the range 8..11 maps to a minimum
 * rating of 3.
 */
public class MatchRatingApproachEncoderTest_testgetMinRating_10_Returns3_Successfully
        extends AbstractStringEncoderTest<MatchRatingApproachEncoder> {

    @Override
    protected MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    @Test
    final void getMinRating_forCombinedLengthOf10_returns3() {
        final MatchRatingApproachEncoder encoder = getStringEncoder();

        final int combinedNameLength = 10;
        final int expectedMinRating = 3;

        assertEquals(expectedMinRating, encoder.getMinRating(combinedNameLength));
    }
}
