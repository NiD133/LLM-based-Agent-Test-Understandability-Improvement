package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

public class MatchRatingApproachEncoderTest_testIsVowel_SmallD_ReturnsFalse {

    private static final String LOWERCASE_CONSONANT = "d";
    private final MatchRatingApproachEncoder stringEncoder = new MatchRatingApproachEncoder();

    private MatchRatingApproachEncoder getStringEncoder() {
        return stringEncoder;
    }

    @Test
    final void testIsVowel_SmallD_ReturnsFalse() {
        assertFalse(getStringEncoder().isVowel(LOWERCASE_CONSONANT));
    }
}
