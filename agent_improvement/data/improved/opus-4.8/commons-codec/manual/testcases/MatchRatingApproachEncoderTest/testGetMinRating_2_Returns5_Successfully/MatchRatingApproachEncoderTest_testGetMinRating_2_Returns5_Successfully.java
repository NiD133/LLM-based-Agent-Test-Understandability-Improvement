package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

/**
 * Tests {@link MatchRatingApproachEncoder#getMinRating(int)}.
 *
 * <p>{@code getMinRating} maps the combined length of two encoded names to a
 * minimum similarity rating: the shorter the combined length, the higher the
 * required rating. For a combined length of 4 or less, the algorithm requires
 * the highest rating, which is 5.</p>
 */
public class MatchRatingApproachEncoderTest_testGetMinRating_2_Returns5_Successfully
        extends AbstractStringEncoderTest<MatchRatingApproachEncoder> {

    @Override
    protected MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    @Test
    final void getMinRatingForCombinedLengthOf2ReturnsHighestRating5() {
        // Given: a combined name length of 2, which falls in the "<= 4" band
        final int combinedLength = 2;
        final int expectedMinRating = 5;

        // When
        final int actualMinRating = getStringEncoder().getMinRating(combinedLength);

        // Then
        assertEquals(expectedMinRating, actualMinRating);
    }
}
