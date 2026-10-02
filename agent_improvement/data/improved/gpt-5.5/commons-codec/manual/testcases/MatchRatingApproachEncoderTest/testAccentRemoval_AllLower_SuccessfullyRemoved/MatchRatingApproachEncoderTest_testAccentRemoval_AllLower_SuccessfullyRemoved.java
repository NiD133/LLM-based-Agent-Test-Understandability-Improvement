package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class MatchRatingApproachEncoderTest_testAccentRemoval_AllLower_SuccessfullyRemoved {

    private final MatchRatingApproachEncoder stringEncoder = createStringEncoder();

    private MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    private MatchRatingApproachEncoder getStringEncoder() {
        return stringEncoder;
    }

    @Test
    final void testAccentRemoval_AllLower_SuccessfullyRemoved() {
        final String accentedLowercaseVowels = "áéíóú";
        final String plainLowercaseVowels = "aeiou";

        assertEquals(plainLowercaseVowels, getStringEncoder().removeAccents(accentedLowercaseVowels));
    }
}
