package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

/**
 * Verifies that {@code removeDoubleConsonants} only collapses repeated consonants,
 * not repeated vowels. "BEETLE" contains the double vowel "EE", which the method
 * must leave intact, so the output equals the input.
 */
public class MatchRatingApproachEncoderTest_testRemoveDoubleDoubleVowel_BEETLE_NotRemoved extends AbstractStringEncoderTest<MatchRatingApproachEncoder> {

    @Override
    protected MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    @Test
    final void testRemoveDoubleDoubleVowel_BEETLE_NotRemoved() {
        // "EE" is a double vowel, not a double consonant, so it must not be collapsed.
        String input = "BEETLE";
        String expected = "BEETLE";
        assertEquals(expected, getStringEncoder().removeDoubleConsonants(input));
    }
}
