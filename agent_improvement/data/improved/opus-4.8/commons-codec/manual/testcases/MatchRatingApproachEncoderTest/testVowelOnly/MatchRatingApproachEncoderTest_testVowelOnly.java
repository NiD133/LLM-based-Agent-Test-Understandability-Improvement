package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

/**
 * Verifies how {@link MatchRatingApproachEncoder} handles an input made up entirely of vowels.
 *
 * <p>The Match Rating Approach algorithm deletes every vowel from a name <em>except</em> the one
 * that begins the word. When the input contains nothing but vowels, every vowel after the first is
 * stripped away, leaving only the (upper-cased) leading vowel as the resulting code.</p>
 */
public class MatchRatingApproachEncoderTest_testVowelOnly extends AbstractStringEncoderTest<MatchRatingApproachEncoder> {

    @Override
    protected MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    @Test
    final void testVowelOnly() {
        // Input is all vowels (mixed case); only the leading vowel survives, upper-cased to "A".
        final String allVowels = "aeiouAEIOU";
        final String expectedCode = "A";

        assertEquals(getStringEncoder().encode(allVowels), expectedCode);
    }
}
