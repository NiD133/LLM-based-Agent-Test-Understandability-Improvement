package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

/**
 * Verifies {@link MatchRatingApproachEncoder#getMinRating(int)} for a combined
 * name length that falls in the 5-to-7 band.
 *
 * <p>Per the Match Rating Approach specification, a summed length of 5 must map
 * to a minimum similarity rating of 4.</p>
 */
public class MatchRatingApproachEncoderTest_testgetMinRating_5_Returns4_Successfully2
        extends AbstractStringEncoderTest<MatchRatingApproachEncoder> {

    @Override
    protected MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    @Test
    final void getMinRatingForSummedLengthOf5ReturnsRating4() {
        final int summedNameLength = 5;
        final int expectedMinRating = 4;

        final int actualMinRating = getStringEncoder().getMinRating(summedNameLength);

        assertEquals(expectedMinRating, actualMinRating);
    }
}
