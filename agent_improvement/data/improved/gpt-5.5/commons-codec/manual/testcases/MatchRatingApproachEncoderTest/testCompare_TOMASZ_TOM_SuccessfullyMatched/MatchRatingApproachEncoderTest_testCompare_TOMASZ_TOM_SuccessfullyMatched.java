package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class MatchRatingApproachEncoderTest_testCompare_TOMASZ_TOM_SuccessfullyMatched {

    private static final String FULL_NAME = "Tomasz";
    private static final String SHORT_NAME = "tom";

    @Test
    final void testCompare_TOMASZ_TOM_SuccessfullyMatched() {
        final MatchRatingApproachEncoder encoder = new MatchRatingApproachEncoder();

        assertTrue(encoder.isEncodeEquals(FULL_NAME, SHORT_NAME));
    }
}
