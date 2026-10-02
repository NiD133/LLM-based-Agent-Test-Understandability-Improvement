package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class MatchRatingApproachEncoderTest_testGetFirstLast3_PETE_Returns_PETE {

    private MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    private MatchRatingApproachEncoder getStringEncoder() {
        return createStringEncoder();
    }

    @Test
    final void testGetFirstLast3_PETE_Returns_PETE() {
        // Names with six or fewer characters are returned unchanged.
        assertEquals("PETE", getStringEncoder().getFirst3Last3("PETE"));
    }
}
