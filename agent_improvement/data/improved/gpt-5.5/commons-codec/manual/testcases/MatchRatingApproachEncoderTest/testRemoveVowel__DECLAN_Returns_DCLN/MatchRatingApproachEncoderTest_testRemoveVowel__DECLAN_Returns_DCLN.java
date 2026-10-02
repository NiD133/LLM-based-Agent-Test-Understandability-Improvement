package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class MatchRatingApproachEncoderTest_testRemoveVowel__DECLAN_Returns_DCLN {

    private final MatchRatingApproachEncoder stringEncoder = createStringEncoder();

    private MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    private MatchRatingApproachEncoder getStringEncoder() {
        return stringEncoder;
    }

    @Test
    final void testRemoveVowel__DECLAN_Returns_DCLN() {
        final String nameWithVowels = "DECLAN";
        final String nameWithoutNonInitialVowels = "DCLN";

        assertEquals(nameWithoutNonInitialVowels, getStringEncoder().removeVowels(nameWithVowels));
    }
}
