package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

/**
 * Verifies {@link MatchRatingApproachEncoder#getMinRating(int)} for the smallest combined name length.
 *
 * <p>The minimum rating decreases as the summed length of the two compared names grows. The shortest
 * bucket (a combined length of 4 or fewer characters) must yield the highest minimum rating of 5.</p>
 */
public class MatchRatingApproachEncoderTest_testGetMinRating_1_Returns5_Successfully
        extends AbstractStringEncoderTest<MatchRatingApproachEncoder> {

    @Override
    protected MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    @Test
    final void getMinRatingReturns5ForCombinedLengthOf1() {
        final int combinedNameLength = 1;
        final int expectedMinRating = 5;

        final int actualMinRating = getStringEncoder().getMinRating(combinedNameLength);

        assertEquals(expectedMinRating, actualMinRating,
                "A combined name length of 1 falls in the shortest bucket (<= 4), so the minimum rating must be 5");
    }
}
