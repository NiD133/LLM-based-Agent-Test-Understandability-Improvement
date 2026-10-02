package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link MatchRatingApproachEncoder#removeAccents(String)} strips the
 * accents from lower-case accented vowels while preserving their lower-case form.
 */
public class MatchRatingApproachEncoderTest_testAccentRemoval_AllLower_SuccessfullyRemoved
        extends AbstractStringEncoderTest<MatchRatingApproachEncoder> {

    @Override
    protected MatchRatingApproachEncoder createStringEncoder() {
        return new MatchRatingApproachEncoder();
    }

    @Test
    final void testAccentRemoval_AllLower_SuccessfullyRemoved() {
        // The five lower-case acute-accented vowels (á, é, í, ó, ú).
        final String accentedLowerVowels = "áéíóú";

        // Each accented vowel should map to its plain lower-case ASCII equivalent.
        final String deAccented = getStringEncoder().removeAccents(accentedLowerVowels);

        assertEquals("aeiou", deAccented);
    }
}
