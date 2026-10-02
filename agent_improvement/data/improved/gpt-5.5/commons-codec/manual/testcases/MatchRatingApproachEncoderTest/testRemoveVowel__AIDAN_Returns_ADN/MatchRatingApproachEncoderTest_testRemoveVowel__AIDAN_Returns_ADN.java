package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class MatchRatingApproachEncoderTest_testRemoveVowel__AIDAN_Returns_ADN {

    @Test
    final void removeVowelsRetainsInitialVowelAndRemovesLaterVowels() {
        final String nameWithInitialVowel = "AIDAN";
        final String encodedNameWithoutNonInitialVowels = "ADN";

        final MatchRatingApproachEncoder encoder = new MatchRatingApproachEncoder();
        assertEquals(encodedNameWithoutNonInitialVowels, encoder.removeVowels(nameWithInitialVowel));
    }
}
