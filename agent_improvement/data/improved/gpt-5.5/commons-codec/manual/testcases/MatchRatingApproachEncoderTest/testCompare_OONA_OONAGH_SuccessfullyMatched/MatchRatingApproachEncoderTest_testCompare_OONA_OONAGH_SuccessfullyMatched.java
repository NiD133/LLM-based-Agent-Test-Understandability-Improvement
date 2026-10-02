package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class MatchRatingApproachEncoderTest_testCompare_OONA_OONAGH_SuccessfullyMatched {

    private static final String FIRST_NAME = "Oona";
    private static final String SECOND_NAME = "Oonagh";

    @Test
    final void testCompare_OONA_OONAGH_SuccessfullyMatched() {
        final MatchRatingApproachEncoder encoder = new MatchRatingApproachEncoder();

        assertTrue(
                encoder.isEncodeEquals(FIRST_NAME, SECOND_NAME),
                "Expected the Match Rating Approach encoder to match Oona and Oonagh");
    }
}
