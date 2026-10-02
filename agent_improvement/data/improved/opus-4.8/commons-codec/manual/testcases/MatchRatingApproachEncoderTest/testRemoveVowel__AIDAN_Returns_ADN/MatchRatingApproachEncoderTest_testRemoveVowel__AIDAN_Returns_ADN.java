package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

/**
 * Verifies {@link MatchRatingApproachEncoder#removeVowels(String)}.
 * <p>
 * The Match Rating Approach algorithm deletes every vowel (A, E, I, O, U) from a
 * name, with one exception: a vowel that begins the word is kept. For the input
 * {@code "AIDAN"} this means:
 * </p>
 * <ul>
 *   <li>{@code A} (leading vowel) is preserved,</li>
 *   <li>the remaining {@code I} and {@code A} vowels are deleted,</li>
 *   <li>the consonants {@code D} and {@code N} are left untouched,</li>
 * </ul>
 * <p>
 * leaving the encoded value {@code "ADN"}.
 * </p>
 */
public class MatchRatingApproachEncoderTest_testRemoveVowel__AIDAN_Returns_ADN
        extends AbstractStringEncoderTest<MatchRatingApproachEncoder> {

    @Override
    protected MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    @Test
    final void testRemoveVowel__AIDAN_Returns_ADN() {
        final String nameWithVowels = "AIDAN";
        final String expectedWithoutVowels = "ADN";

        final String actual = getStringEncoder().removeVowels(nameWithVowels);

        assertEquals(expectedWithoutVowels, actual,
                "Leading vowel 'A' should be kept while inner vowels 'I' and 'A' are removed");
    }
}
