package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

/**
 * Verifies {@link MatchRatingApproachEncoder#getMinRating(int)}.
 * <p>
 * {@code getMinRating} maps the combined length of two encoded names to the
 * minimum similarity rating required for the names to be considered a match.
 * The longer the combined length, the lower (more lenient) the required rating.
 * </p>
 */
public class MatchRatingApproachEncoderTest_testGetMinRating_7_Return4_Successfully
        extends AbstractStringEncoderTest<MatchRatingApproachEncoder> {

    @Override
    protected MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    /**
     * A combined name length of 7 falls in the "5 to 7" band, which requires a
     * minimum rating of 4.
     */
    @Test
    final void testGetMinRating_7_Return4_Successfully() {
        final int combinedNameLength = 7;
        final int expectedMinRating = 4;

        final int actualMinRating = getStringEncoder().getMinRating(combinedNameLength);

        assertEquals(expectedMinRating, actualMinRating);
    }
}
