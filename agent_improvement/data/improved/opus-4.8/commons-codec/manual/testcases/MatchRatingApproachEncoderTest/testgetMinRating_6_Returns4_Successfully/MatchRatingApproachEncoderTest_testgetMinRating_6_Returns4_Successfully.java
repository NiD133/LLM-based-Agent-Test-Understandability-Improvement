package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

/**
 * Verifies {@link MatchRatingApproachEncoder#getMinRating(int)} for an input
 * that falls into the "5 to 7" combined-length bucket.
 *
 * <p>The algorithm maps the summed length of two encoded names to a minimum
 * similarity rating. A sum of 6 sits in the inclusive 5-7 range, which the
 * documentation defines as a minimum rating of 4.</p>
 */
public class MatchRatingApproachEncoderTest_testgetMinRating_6_Returns4_Successfully
        extends AbstractStringEncoderTest<MatchRatingApproachEncoder> {

    @Override
    protected MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    @Test
    final void getMinRatingForCombinedLength6ReturnsRating4() {
        final int combinedNameLength = 6;
        final int expectedMinRating = 4;

        final int actualMinRating = getStringEncoder().getMinRating(combinedNameLength);

        assertEquals(expectedMinRating, actualMinRating,
                "A combined name length of 6 should yield a minimum rating of 4");
    }
}
