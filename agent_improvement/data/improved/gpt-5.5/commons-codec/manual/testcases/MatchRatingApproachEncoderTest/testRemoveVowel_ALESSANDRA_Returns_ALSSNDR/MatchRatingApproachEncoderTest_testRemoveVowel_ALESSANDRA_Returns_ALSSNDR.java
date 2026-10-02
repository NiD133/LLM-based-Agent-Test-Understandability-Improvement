package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class MatchRatingApproachEncoderTest_testRemoveVowel_ALESSANDRA_Returns_ALSSNDR {

    private static final String NAME_WITH_INITIAL_VOWEL = "ALESSANDRA";
    private static final String NAME_WITH_INTERNAL_VOWELS_REMOVED = "ALSSNDR";

    @Test
    final void testRemoveVowel_ALESSANDRA_Returns_ALSSNDR() {
        final MatchRatingApproachEncoder encoder = new MatchRatingApproachEncoder();

        assertEquals(NAME_WITH_INTERNAL_VOWELS_REMOVED, encoder.removeVowels(NAME_WITH_INITIAL_VOWEL));
    }
}
