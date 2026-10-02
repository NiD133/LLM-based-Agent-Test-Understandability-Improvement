package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link MatchRatingApproachEncoder#removeDoubleConsonants(String)}
 * only collapses doubled consonants and leaves doubled vowels untouched.
 */
public class MatchRatingApproachEncoderTest_testRemoveDoubleDoubleVowel_BEETLE_NotRemoved
        extends AbstractStringEncoderTest<MatchRatingApproachEncoder> {

    @Override
    protected MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    /**
     * "BEETLE" contains the doubled vowel "EE". Since vowels are not part of the
     * double-consonant table, the word must be returned unchanged.
     */
    @Test
    final void testRemoveDoubleDoubleVowel_BEETLE_NotRemoved() {
        final String wordWithDoubledVowel = "BEETLE";

        final String result = getStringEncoder().removeDoubleConsonants(wordWithDoubledVowel);

        assertEquals("BEETLE", result,
                "Doubled vowel 'EE' must not be collapsed by removeDoubleConsonants");
    }
}
