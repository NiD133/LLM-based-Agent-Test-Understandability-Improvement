package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

public class MatchRatingApproachEncoderTest_testGetMinRating_13_Returns_1_Successfully extends AbstractStringEncoderTest<MatchRatingApproachEncoder> {

    // According to the MRA spec, a combined name-length sum greater than 12 yields the lowest min rating of 1.
    private static final int SUM_LENGTH_ABOVE_MAX_THRESHOLD = 13;
    private static final int EXPECTED_MIN_RATING_FOR_LONG_NAMES = 1;

    @Override
    protected MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    @Test
    final void testGetMinRating_13_Returns_1_Successfully() {
        assertEquals(EXPECTED_MIN_RATING_FOR_LONG_NAMES,
                getStringEncoder().getMinRating(SUM_LENGTH_ABOVE_MAX_THRESHOLD));
    }
}
