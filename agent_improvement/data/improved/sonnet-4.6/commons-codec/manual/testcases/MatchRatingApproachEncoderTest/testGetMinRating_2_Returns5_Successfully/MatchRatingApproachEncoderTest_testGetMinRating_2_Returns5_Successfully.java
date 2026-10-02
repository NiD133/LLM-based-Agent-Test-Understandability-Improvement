package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

public class MatchRatingApproachEncoderTest_testGetMinRating_2_Returns5_Successfully extends AbstractStringEncoderTest<MatchRatingApproachEncoder> {

    @Override
    protected MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    @Test
    final void testGetMinRating_2_Returns5_Successfully() {
        // sumLength <= 4 maps to the highest minimum rating of 5 per the MRA specification
        final int sumLength = 2;
        final int expectedMinRating = 5;
        assertEquals(expectedMinRating, getStringEncoder().getMinRating(sumLength));
    }
}
