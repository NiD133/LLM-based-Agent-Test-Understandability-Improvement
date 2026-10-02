package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

/**
 * Verifies {@link MatchRatingApproachEncoder#removeVowels(String)}.
 *
 * <p>The algorithm deletes every vowel (A, E, I, O, U) from a name, but keeps a
 * leading vowel when the word starts with one. Here the input "DECLAN" begins
 * with the consonant "D", so all of its vowels ("E" and "A") are removed,
 * leaving "DCLN".</p>
 */
public class MatchRatingApproachEncoderTest_testRemoveVowel__DECLAN_Returns_DCLN
        extends AbstractStringEncoderTest<MatchRatingApproachEncoder> {

    @Override
    protected MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    @Test
    final void removeVowels_dropsAllVowelsWhenWordStartsWithConsonant() {
        final String input = "DECLAN";
        final String expectedWithoutVowels = "DCLN";

        final String actual = getStringEncoder().removeVowels(input);

        assertEquals(expectedWithoutVowels, actual);
    }
}
