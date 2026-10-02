package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class MatchRatingApproachEncoderTest_testVowelOnly {

    private static final String VOWELS_IN_LOWER_AND_UPPER_CASE = "aeiouAEIOU";
    private static final String EXPECTED_ENCODING_FOR_VOWEL_ONLY_INPUT = "A";

    private MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    private MatchRatingApproachEncoder getStringEncoder() {
        return createStringEncoder();
    }

    @Test
    final void testVowelOnly() {
        assertEquals(EXPECTED_ENCODING_FOR_VOWEL_ONLY_INPUT, getStringEncoder().encode(VOWELS_IN_LOWER_AND_UPPER_CASE));
    }
}
