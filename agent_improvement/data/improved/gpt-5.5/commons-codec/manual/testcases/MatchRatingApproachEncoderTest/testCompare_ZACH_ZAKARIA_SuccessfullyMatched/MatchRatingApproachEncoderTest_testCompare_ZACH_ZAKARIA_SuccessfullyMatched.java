package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class MatchRatingApproachEncoderTest_testCompare_ZACH_ZAKARIA_SuccessfullyMatched {

    private static final String SHORT_NAME = "Zach";
    private static final String LONGER_RELATED_NAME = "Zacharia";

    private MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    private MatchRatingApproachEncoder getStringEncoder() {
        return createStringEncoder();
    }

    @Test
    final void testCompare_ZACH_ZAKARIA_SuccessfullyMatched() {
        assertTrue(getStringEncoder().isEncodeEquals(SHORT_NAME, LONGER_RELATED_NAME));
    }
}
