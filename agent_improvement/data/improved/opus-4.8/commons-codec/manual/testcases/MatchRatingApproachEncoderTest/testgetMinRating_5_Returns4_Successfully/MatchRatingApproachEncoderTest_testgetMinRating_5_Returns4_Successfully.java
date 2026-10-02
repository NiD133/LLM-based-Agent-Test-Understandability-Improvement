package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

/**
 * Tests {@link MatchRatingApproachEncoder#getMinRating(int)}.
 *
 * <p>{@code getMinRating} maps the combined length of two encoded names to the
 * minimum similarity rating required for them to be considered a match: the
 * longer the combined names, the smaller the required rating. Per the algorithm,
 * a combined length in the range 5..7 requires a minimum rating of 4.</p>
 */
public class MatchRatingApproachEncoderTest_testgetMinRating_5_Returns4_Successfully
        extends AbstractStringEncoderTest<MatchRatingApproachEncoder> {

    @Override
    protected MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    @Test
    final void testGetMinRatingForCombinedLengthOf5Returns4() {
        // Arrange
        final MatchRatingApproachEncoder encoder = getStringEncoder();
        final int combinedNameLength = 5;
        final int expectedMinRating = 4;

        // Act
        final int actualMinRating = encoder.getMinRating(combinedNameLength);

        // Assert
        assertEquals(expectedMinRating, actualMinRating);
    }
}
