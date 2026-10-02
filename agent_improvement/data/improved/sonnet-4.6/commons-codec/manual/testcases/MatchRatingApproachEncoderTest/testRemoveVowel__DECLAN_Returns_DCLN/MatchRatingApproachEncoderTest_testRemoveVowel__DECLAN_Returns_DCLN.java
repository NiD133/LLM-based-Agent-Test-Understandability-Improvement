package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class MatchRatingApproachEncoderTest_testRemoveVowel__DECLAN_Returns_DCLN extends AbstractStringEncoderTest<MatchRatingApproachEncoder> {

    @Override
    protected MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    /**
     * Verifies that interior vowels (E, A) are stripped from "DECLAN",
     * leaving only the consonants "DCLN". The initial 'D' is a consonant,
     * so the MRA rule "preserve leading vowel" does not apply here.
     */
    @Test
    @DisplayName("removeVowels(\"DECLAN\") strips interior vowels E and A, returning \"DCLN\"")
    final void testRemoveVowel__DECLAN_Returns_DCLN() {
        assertEquals("DCLN", getStringEncoder().removeVowels("DECLAN"));
    }
}
