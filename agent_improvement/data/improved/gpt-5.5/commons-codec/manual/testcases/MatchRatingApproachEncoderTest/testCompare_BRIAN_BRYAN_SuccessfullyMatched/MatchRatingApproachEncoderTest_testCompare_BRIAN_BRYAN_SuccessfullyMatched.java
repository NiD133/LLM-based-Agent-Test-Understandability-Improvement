package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class MatchRatingApproachEncoderTest_testCompare_BRIAN_BRYAN_SuccessfullyMatched {

    private static final String FIRST_NAME = "Brian";
    private static final String SIMILAR_SOUNDING_NAME = "Bryan";

    @Test
    final void testCompare_BRIAN_BRYAN_SuccessfullyMatched() {
        final MatchRatingApproachEncoder encoder = new MatchRatingApproachEncoder();

        assertTrue(encoder.isEncodeEquals(FIRST_NAME, SIMILAR_SOUNDING_NAME));
    }
}
