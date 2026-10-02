package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

public class MatchRatingApproachEncoderTest_testgetMinRating_5_Returns4_Successfully2 extends AbstractStringEncoderTest<MatchRatingApproachEncoder> {

    @Override
    protected MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    /**
     * Combined name-length of 5 falls in the [5, 7] bucket of the MRA lookup table,
     * so getMinRating must return a minimum rating of 4.
     */
    @Test
    final void testgetMinRating_5_Returns4_Successfully2() {
        // A combined encoded-name length in the range [5, 7] maps to min rating 4
        int combinedNameLength = 5;
        int expectedMinRating = 4;

        int actualMinRating = getStringEncoder().getMinRating(combinedNameLength);

        assertEquals(expectedMinRating, actualMinRating);
    }
}
