package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

/**
 * Verifies {@link MatchRatingApproachEncoder#removeVowels(String)}.
 * <p>
 * The Match Rating Approach algorithm deletes every vowel except one that begins
 * the word. For the input {@code "ALESSANDRA"} the leading {@code A} is kept while
 * the inner vowels (E, A, A) are stripped, yielding {@code "ALSSNDR"}.
 */
class MatchRatingApproachEncoderTest_testRemoveVowel_ALESSANDRA_Returns_ALSSNDR
        extends AbstractStringEncoderTest<MatchRatingApproachEncoder> {

    @Override
    protected MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    @Test
    void removeVowelsKeepsLeadingVowelAndDropsTheRest() {
        final String input = "ALESSANDRA";
        final String expected = "ALSSNDR";

        final String actual = getStringEncoder().removeVowels(input);

        assertEquals(expected, actual,
                "Leading vowel should be preserved while inner vowels are removed");
    }
}
