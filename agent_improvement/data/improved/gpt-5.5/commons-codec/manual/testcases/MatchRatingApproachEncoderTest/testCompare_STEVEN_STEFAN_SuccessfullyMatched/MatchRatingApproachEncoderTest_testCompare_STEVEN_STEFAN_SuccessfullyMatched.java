package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class MatchRatingApproachEncoderTest_testCompare_STEVEN_STEFAN_SuccessfullyMatched {

    private static final String GIVEN_NAME = "Steven";
    private static final String PHONETIC_VARIANT = "Stefan";

    private final MatchRatingApproachEncoder encoder = new MatchRatingApproachEncoder();

    @Test
    final void testCompare_STEVEN_STEFAN_SuccessfullyMatched() {
        assertTrue(
                encoder.isEncodeEquals(GIVEN_NAME, PHONETIC_VARIANT),
                "Expected Steven and Stefan to be considered equivalent by the Match Rating Approach encoder");
    }
}
