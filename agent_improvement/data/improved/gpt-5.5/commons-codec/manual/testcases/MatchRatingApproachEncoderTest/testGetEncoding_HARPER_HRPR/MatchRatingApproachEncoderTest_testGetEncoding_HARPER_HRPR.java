package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class MatchRatingApproachEncoderTest_testGetEncoding_HARPER_HRPR {

    private static final String NAME_TO_ENCODE = "HARPER";
    private static final String EXPECTED_MATCH_RATING_ENCODING = "HRPR";

    private MatchRatingApproachEncoder getStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    @Test
    final void testGetEncoding_HARPER_HRPR() {
        assertEquals(EXPECTED_MATCH_RATING_ENCODING, getStringEncoder().encode(NAME_TO_ENCODE));
    }
}
